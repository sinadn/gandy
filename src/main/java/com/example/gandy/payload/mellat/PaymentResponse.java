package com.example.gandy.payload.mellat;

import lombok.Builder;

@Builder
public record PaymentResponse(String url, String method, String body, String authority) {}
