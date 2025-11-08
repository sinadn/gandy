package com.example.gandy.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CallbackResponse {
    private String authority;
    private String saleReferenceId;
    private String description;
    private Boolean isSuccessful;
}
