package com.example.gandy.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankCardRequest {
    private Long id;
    private String name;
    private String family;
    private String shaba;
    private String cardNum;
}
