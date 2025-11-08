package com.example.gandy.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyRequest {
    public String paymentId;
    public String saleReferenceId;
}
