package com.example.gandy.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyResponse {
    private Boolean verified;
    private String description;

    public VerifyResponse(boolean b, String description) {

    }
}
