package com.example.gandy.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayResponse {
    private String url;
    private String method;
    private String body;
    private String authority;
}
