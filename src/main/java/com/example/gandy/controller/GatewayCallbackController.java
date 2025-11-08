package com.example.gandy.controller;

import com.example.gandy.util.ResponseCodeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(GatewayCallbackController.CALLBACK_URL)
@CrossOrigin(origins = "https://bpm.shaparak.ir")  // جایگزین کنید با دامنه واقعی بانک ملت
public class GatewayCallbackController {

    public static final String CALLBACK_URL = "/api/gateway-callback";

    private final String paymentCallback;

    public GatewayCallbackController(
            @Value("${payment.service.protocol}") String protocol,
            @Value("${payment.service.hostname}") String hostName,
            @Value("${payment.service.port}") String port,
            @Value("${payment.service.path}") String path

    ) {
        this.paymentCallback =
                String.format(
                        "%s://%s:%s%s",
                        protocol, hostName, port, path);
        ;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> receivedCallbackPost(@RequestBody String body) {
        Map<String, String> response = convertResponse(body);
        boolean isSuccessful = false;
        String authority = null;
        String saleReferenceId = null;
        String description = null;

        String code = response.get("ResCode");

        if (code.equals("0")) {
            isSuccessful = true;
            authority = response.get("RefId");
            saleReferenceId = response.get("SaleReferenceId");
        } else {
            authority = response.get("RefId");
            description = ResponseCodeUtils.getDescription(code);
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(paymentCallback);
        if (authority != null) {
            uriBuilder.queryParam("authority", authority);
        }
        if (saleReferenceId != null) {
            uriBuilder.queryParam("saleReferenceId", saleReferenceId);
        }
        if (description != null) {
            uriBuilder.queryParam("description", description);
        }
        uriBuilder.queryParam("isSuccessful", isSuccessful);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", uriBuilder.build().toUriString().replace(":/", "://"))
                .build();
    }

    private Map<String, String> convertResponse(String body) {
        String[] response = body.split("&");
        Map<String, String> responseObject = new HashMap<>();
        for (String entry : response) {
            String[] entrySplit = entry.split("=");
            responseObject.put(entrySplit[0], entrySplit[1]);
        }

        return responseObject;
    }
}
