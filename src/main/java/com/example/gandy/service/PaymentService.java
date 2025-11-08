package com.example.gandy.service;



import com.example.gandy.IPaymentGateway;
import com.example.gandy.PaymentGatewayImplService;
import com.example.gandy.controller.GatewayCallbackController;
import com.example.gandy.exception.GatewayException;
import com.example.gandy.payload.mellat.*;
import com.example.gandy.payload.request.PayRequest;
import com.example.gandy.payload.response.VerifyResponse;
import com.example.gandy.util.ResponseCodeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Service
public class PaymentService {

    private static final Set<String> SUCCESSFUL_VERIFICATION_CODES = new HashSet<>();
    private static String CALLBACK_ADDRESS = "";

    static {
        SUCCESSFUL_VERIFICATION_CODES.add("0"); // TODO write all success messages in list
    }

    //    private final IPaymentGateway gateway;
    private final PaymentGatewayImplService gatewayImplService;
    private final long terminalId;
    private final String username;
    private final String password;
    private final String gatewayUrl;

    public PaymentService(
            PaymentGatewayImplService gatewayService,
            @Value("${mellat.terminal-id}") long terminalId,
            @Value("${mellat.username}") String username,
            @Value("${mellat.password}") String password,
            @Value("${mellat.gateway-url}") String gatewayUrl,
            @Value("${this.hostname}") String hostName,
            @Value("${server.port}") String port,
            @Value("${this.protocol}") String protocol) {
        this.gatewayImplService = gatewayService;
        this.terminalId = terminalId;
        this.username = username;
        this.password = password;
        this.gatewayUrl = gatewayUrl;
        CALLBACK_ADDRESS =
                String.format(
                        "%s://%s:%s%s",
                        protocol, hostName, port, GatewayCallbackController.CALLBACK_URL);
    }

    public PaymentResponse requestNewPayment(PayRequest request) {
        IPaymentGateway gateway = gatewayImplService.getPaymentGatewayImplPort();
        String[] response =
                gateway.bpPayRequest(
                                this.terminalId,
                                this.username,
                                this.password,
                                Long.parseLong(request.getPaymentId()),
                                request.getAmount(),
                                this.getCurrentDate(),
                                this.getCurrentTime(),
                                request.getDescription(),
                                PaymentService.CALLBACK_ADDRESS,
                                request.getPayerId(),
                                null,
                                null,
                                null,
                                null,
                                null)
                        .split(",");
        String code;
        String refId;
        if (response.length == 2) {
            code = response[0];
            refId = response[1];
        } else if (response.length < 2) {
            code = response[0];
            throw new GatewayException("Received invalid code: " + code);
        } else {
            throw new GatewayException("Received invalid respose");
        }

        return PaymentResponse.builder()
                .url(gatewayUrl)
                .method(HttpMethod.POST.toString())
                .body(refId)
                .authority(refId)
                .build();
    }

    public VerificationResponse verify(VerifyRequest request) {
        IPaymentGateway gateway = gatewayImplService.getPaymentGatewayImplPort();
        String response =
                gateway.bpVerifyRequest(
                        this.terminalId,
                        this.username,
                        this.password,
                        Long.parseLong(request.paymentId()),
                        Long.parseLong(request.paymentId()),
                        Long.parseLong(request.saleReferenceId()));

        if (response.equals("0")) {
            return new VerificationResponse(true, null);
        }
        response =
                gateway.bpInquiryRequest(
                        this.terminalId,
                        this.username,
                        this.password,
                        Long.parseLong(request.paymentId()),
                        Long.parseLong(request.paymentId()),
                        Long.parseLong(request.saleReferenceId()));
        if (SUCCESSFUL_VERIFICATION_CODES.contains(response.intern())) {
            return new VerificationResponse(true, null);
        }
        return new VerificationResponse(false, ResponseCodeUtils.getDescription(response));
    }


    public TransactionResponse settle(SettleRequest request) {
        IPaymentGateway gateway = gatewayImplService.getPaymentGatewayImplPort();
        String response =
                gateway.bpSettleRequest(
                        this.terminalId,
                        this.username,
                        this.password,
                        Long.parseLong(request.paymentId()),
                        Long.parseLong(request.paymentId()),
                        Long.parseLong(request.saleReferenceId()));
        if (response.equals("0")) {
            return new TransactionResponse(true);
        }
        return new TransactionResponse(false, ResponseCodeUtils.getDescription(response));
    }

    public TransactionResponse rollback(RollbackRequest request) {
        IPaymentGateway gateway = gatewayImplService.getPaymentGatewayImplPort();

        String response =
                gateway.bpReversalRequest(
                        this.terminalId,
                        this.username,
                        this.password,
                        Long.parseLong(request.paymentId()),
                        Long.parseLong(request.paymentId()),
                        Long.parseLong(request.saleReferenceId()));

        if (response.equals("0")) {
            return new TransactionResponse(true);
        }

        return new TransactionResponse(false, ResponseCodeUtils.getDescription(response));
    }

    private String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
    }
}
