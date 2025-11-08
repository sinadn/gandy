package com.example.gandy.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartRequest {
        private List<RequestProduct> list;
}
