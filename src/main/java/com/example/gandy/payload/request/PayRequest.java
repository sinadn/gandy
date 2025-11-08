package com.example.gandy.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayRequest {
    public String paymentId;
    public String payerId;
    public String description;
    public long amount;
}
