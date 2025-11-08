package com.example.gandy.controller;//package com.example.gandymobile.controller;

//import com.example.gandy.soapres.com.bps.sw.pgw.service.IPaymentGateway;
//import com.example.gandy.soapres.com.bps.sw.pgw.service.PaymentGatewayImplService;

import com.example.gandy.entity.*;
import com.example.gandy.payload.mellat.*;
import com.example.gandy.payload.request.PayRequest;
import com.example.gandy.payload.request.VerifyRequest;
import com.example.gandy.payload.response.*;
import com.example.gandy.repo.CartRepository;
import com.example.gandy.repo.ShipmentStatusRepository;
import com.example.gandy.service.FactorServiceImpl;
import com.example.gandy.service.PaymentService;
import com.example.gandy.service.ProductCountServiceImpl;
import com.example.gandy.service.payment.PaymentServiceImpl;
import com.example.gandy.utility.DiscountCalculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {


    @Autowired
    PaymentServiceImpl service2;

    @Autowired
    FactorServiceImpl factorService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductCountServiceImpl productCountService;

    @Autowired
    ShipmentStatusRepository shipmentStatusRepository;


    @Autowired
    private PaymentService paymentService;


    @PostMapping("/payRequest")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> requestPayment() throws IOException, InterruptedException, URISyntaxException {
        Factor factor = null;
        PayRequest payRequest = new PayRequest();
        Random randomNumber = new Random();
        long random = 10000 + randomNumber.nextLong(90000);
        factor = factorService.findFactorByUserAndId();
        Payment payment = factor.getPayment();
        payment.setPaymentId(random);
        long amount = calculatePrice(factor.getId());
        amount += factor.getShipmentPrice();
        payRequest.setPayerId(String.valueOf(factor.getUsers().getId()));
        payRequest.setDescription("pay product from gandimobile");
        payRequest.setPaymentId(String.valueOf(payment.getPaymentId()));
        String val = String.valueOf(amount) + "0";
//        String val = String.valueOf(5000) + "0";

        payRequest.setAmount(Long.parseLong(val));
        PaymentResponse paymentResponse =  paymentService.requestNewPayment(payRequest);
        assert paymentResponse != null;
        payment.setAuthority(paymentResponse.authority());
        service2.addObject(payment);
        return ResponseEntity.ok(paymentResponse);
    }


    @GetMapping("/callback")
    public ModelAndView callback(CallbackResponse callbackResponse) throws IOException, URISyntaxException, InterruptedException {
        Payment payment = new Payment();
        payment = service2.findByAuthority(callbackResponse.getAuthority());
        if (callbackResponse.getIsSuccessful()) {
            RollbackResponse rollbackResponse = new RollbackResponse();
//            VerifyResponse verifyResponse = verifyPay(verifyRequest);
//            VerifyRequest verifyRequest = new VerifyRequest();
//            verifyRequest.setSaleReferenceId(callbackResponse.getSaleReferenceId());
//            verifyRequest.setPaymentId(String.valueOf(payment.getPaymentId()));

            com.example.gandy.payload.mellat.VerifyRequest verifyRequest1 = new com.example.gandy.payload.mellat.VerifyRequest(String.valueOf(payment.getPaymentId()) ,callbackResponse.getSaleReferenceId());
            VerificationResponse verifyResponse =  paymentService.verify(verifyRequest1);

            if (verifyResponse.verified()) {
                if (reduceProduct(callbackResponse.getAuthority())) {
                    SettleRequest settleRequest = new SettleRequest(String.valueOf(payment.getPaymentId()) ,callbackResponse.getSaleReferenceId());
                    TransactionResponse transactionResponse =  paymentService.settle(settleRequest);

//                    rollbackResponse = settlePay(verifyRequest1);
                    if (transactionResponse.isSuccessful()){
                        payment.setStatus(Status.SETTLED);
                        payment.setIsSuccessful(rollbackResponse.getIsSuccessful());
                        payment.setSaleReferenceId(callbackResponse.getSaleReferenceId());
                        service2.addObject(payment);
                        registerFactor(callbackResponse.getAuthority());
                        ModelAndView model1 = new ModelAndView("success-card/successful");
                        model1.addObject("trace" , callbackResponse.getSaleReferenceId());
                        return model1;
                    }
                } else {
                    RollbackRequest rollbackRequest = new RollbackRequest(String.valueOf(payment.getPaymentId()) ,callbackResponse.getSaleReferenceId());
                    TransactionResponse transactionResponse =  paymentService.rollback(rollbackRequest);
                    if (transactionResponse.isSuccessful()){
                        payment.setStatus(Status.ROLLBACK);
                        payment.setIsSuccessful(false);
                        service2.addObject(payment);
                        rollBackFactor(callbackResponse.getAuthority());
                        return new ModelAndView("error-card/error-payment");
                    }
                }
            }else {
                return new ModelAndView("error-card/error-payment");
            }
        } else {
            payment.setStatus(Status.CANCELED);
            payment.setIsSuccessful(false);
            service2.addObject(payment);
            cancelFactor(callbackResponse.getAuthority());
            return new ModelAndView("error-card/error-payment");
        }
        return new ModelAndView("success-card/error-payment");
    }




    public long calculatePrice(long id) {
        Factor factor1 = null;
        List<Cart> cartList = null;
        long result = 0;

        cartList = cartRepository.findCartsByFactorId(id);
        if (cartList != null) {
            List<ProductCount> productCountList = new ArrayList<>();
            for (Cart item : cartList) {
                productCountList.add(item.getProductCount());
            }
            DiscountCalculation discountCalculation = new DiscountCalculation();
            List<ProductCountResponse> productCountResponse = discountCalculation.calculation(productCountList);
            for (Cart item : cartList) {
                for (ProductCountResponse pcount : productCountResponse) {
                    if (item.getProductCount().getId() == pcount.getId()) {
                        item.setFactorFinalPrice(pcount.getFinalPrice());
                        result += item.getFactorFinalPrice() * item.getCount();
                        item.setFactorMainPrice(pcount.getPrice() * item.getCount());
                        if (pcount.getDiscount() != null) {
                            item.setFactorDiscount(Integer.parseInt(pcount.getDiscount().getDiscount()));
                            item.setFactorDiscountVal(pcount.getDiscountVal());
                        }
                    }
                }
            }
        }
        return result;
    }

    public Boolean reduceProduct(String authority) {
        List<Cart> cartList = new ArrayList<>();
        cartList = cartRepository.findCartsByPaymentAuthority(authority);
        for (Cart cart : cartList) {
            int count = cart.getProductCount().getCount() - cart.getCount();
            if (count < 0) {
                return false;
            } else {
                ProductCount productCount = productCountService.findProductCount(cart.getProductCount().getId());
                productCount.setCount(count);
                productCountService.createObjects(productCount);
            }
        }
        return true;
    }




    public void registerFactor(String authority) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime update_at = LocalDateTime.parse(formatDateTime, formatter);
        Factor factor1 = null;
        List<Cart> cartList = null;
        try {
            factor1 = factorService.findFactorBypaymentAuthority(authority);
        } catch (Exception e) {
            e.getMessage();
        }
        if (factor1 != null) {
            factor1.setUpdate_at(update_at);
            try {
                factor1.setTrackingCode("");
                factor1.setPayWay("خرید آنلاین");
            }catch (Exception e){
                e.getMessage();
            }

                cartList = cartRepository.findCartsByFactorId(factor1.getId());
                if (cartList != null) {
                    List<ProductCount> productCountList = new ArrayList<>();
                    for (Cart item : cartList) {
                        productCountList.add(item.getProductCount());
                    }

                    DiscountCalculation discountCalculation = new DiscountCalculation();
                    List<ProductCountResponse> productCountResponse = discountCalculation.calculation(productCountList);
                    for (Cart item : cartList) {
                        for (ProductCountResponse pcount : productCountResponse) {
                            if (item.getProductCount().getId()==pcount.getId()){
                                item.setFactorFinalPrice(pcount.getFinalPrice());
                                item.setFactorMainPrice(pcount.getPrice());
                                if (pcount.getDiscount()!=null){
                                    item.setFactorDiscount(Integer.parseInt(pcount.getDiscount().getDiscount()));
                                    item.setFactorDiscountVal(pcount.getDiscountVal());
                                }
                            }
                        }
                    }
                    cartRepository.saveAll(cartList);
                }

            Set<ShipmentStatus> finalShipmentStatus = new HashSet<>();
            ShipmentStatus SHIPMENT_ACCEPT = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_ACCEPT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            finalShipmentStatus.add(SHIPMENT_ACCEPT);
            sendSms("کالای مورد نظر شما در سایت موبایل گاندی ثبت شد با تشکر موبایل گاندی",factor1.getUsers().getMobile());
            sendSms("یک خرید توسط مشتری انجام شده است لطفا بخش سفارشات در پنل مدیریت را مشاهده کنید","09334038808");
            sendSms("یک خرید توسط مشتری انجام شده است لطفا بخش سفارشات در پنل مدیریت را مشاهده کنید","09397093219");
            factor1.setShipmentStatus(finalShipmentStatus);
        }
        factorService.addObject(factor1);
    }



    public void rollBackFactor(String authority) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime update_at = LocalDateTime.parse(formatDateTime, formatter);
        Factor factor1 = null;
        try {
            factor1 = factorService.findFactorBypaymentAuthority(authority);
        } catch (Exception e) {
            e.getMessage();
        }
        if (factor1 != null) {
            factor1.setUpdate_at(update_at);
            try {
                factor1.setTrackingCode("");
                factor1.setPayWay("خرید آنلاین");
            }catch (Exception e){
                e.getMessage();
            }


            Set<ShipmentStatus> finalShipmentStatus = new HashSet<>();
            ShipmentStatus SHIPMENT_ROLLBACK = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_ROLLBACK)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            finalShipmentStatus.add(SHIPMENT_ROLLBACK);
            sendSms("کالای مورد نظر شما در سایت موبایل گاندی ثبت شد با تشکر موبایل گاندی",factor1.getUsers().getMobile());
            factor1.setShipmentStatus(finalShipmentStatus);
        }
        factorService.addObject(factor1);
    }




    public void cancelFactor(String authority) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);
        LocalDateTime update_at = LocalDateTime.parse(formatDateTime, formatter);
        Factor factor1 = null;
        try {
            factor1 = factorService.findFactorBypaymentAuthority(authority);
        } catch (Exception e) {
            e.getMessage();
        }
        if (factor1 != null) {
            factor1.setUpdate_at(update_at);
            try {
                factor1.setTrackingCode("");
                factor1.setPayWay("خرید آنلاین");
            }catch (Exception e){
                e.getMessage();
            }


            Set<ShipmentStatus> finalShipmentStatus = new HashSet<>();
            ShipmentStatus SHIPMENT_CART = shipmentStatusRepository.findByName(EShipmentStatus.SHIPMENT_CART)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            finalShipmentStatus.add(SHIPMENT_CART);
//            sendSms(factor1.getUsers().getMobile());
            factor1.setShipmentStatus(finalShipmentStatus);
        }
        factorService.addObject(factor1);
    }









    public void sendSms(String description,String mobile) {
        final String uri = "https://panel.ghasedaksms.com/tools/urlservice/send/?username=farzad.arabs@gmail.com&password=Sindin46044604!&from=9999178836&to=" + mobile + "&message= " + description + " ";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        System.out.println(result);
    }


}





