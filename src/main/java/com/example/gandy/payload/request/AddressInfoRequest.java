package com.example.gandy.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressInfoRequest {
    public long id;
    public long city;
    public long province;
    public String area;
    public String postalCode;
    public String address;
    public String no;
    public String unit;
}
