package com.example.gandy.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PropertyResponse {
        private List<AttributeTypeResponse> propertylist;
}
