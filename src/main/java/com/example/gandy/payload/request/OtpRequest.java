package com.example.gandy.payload.request;



import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequest {
    @NotBlank
    public String mobile;
    @NotBlank
    public String otp;

}