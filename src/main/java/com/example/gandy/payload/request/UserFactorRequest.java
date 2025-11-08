package com.example.gandy.payload.request;

import com.example.gandy.entity.Address;
import com.example.gandy.entity.Shipment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFactorRequest {
        private Address address;
        private Shipment shipment;
}
