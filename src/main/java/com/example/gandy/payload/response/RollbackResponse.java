package com.example.gandy.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RollbackResponse {
    private Boolean isSuccessful;
    private String errorDescription;
}
