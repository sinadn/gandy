package com.example.gandy.payload.mellat;

import lombok.Builder;

@Builder
public record TransactionResponse(Boolean isSuccessful, String errorDescription) {
    public TransactionResponse(Boolean isSuccessful) {
        this(isSuccessful, null);
    }
}