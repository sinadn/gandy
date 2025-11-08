package com.example.gandy.payload.mellat;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRequest(
        String paymentId, BigDecimal amount, String description, String payerId) {}
