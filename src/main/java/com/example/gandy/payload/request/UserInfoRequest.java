package com.example.gandy.payload.request;

import com.example.gandy.entity.Product;
import com.example.gandy.entity.Users;
import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;

@Getter
@Setter
public class UserInfoRequest {
    public String name;
    public String family;
    public String nationalCode;
    public String mobile;
    public String email;
    public String birthDay;
}
