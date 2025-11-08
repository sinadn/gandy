package com.example.gandy.confiquration;

import com.example.gandy.PaymentGatewayImplService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MellatConfig {
    @Bean
    public PaymentGatewayImplService paymentGatewayImplService() {
        return new PaymentGatewayImplService();
    }
}
