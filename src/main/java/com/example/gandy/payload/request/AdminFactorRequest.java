package com.example.gandy.payload.request;

import com.example.gandy.entity.Address;
import com.example.gandy.entity.Shipment;
import com.example.gandy.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminFactorRequest {
        private long id;
        private String status;
        private long users;
        private int shipmentStatus;
        private String payWay;
        private String trackingCode;
}
