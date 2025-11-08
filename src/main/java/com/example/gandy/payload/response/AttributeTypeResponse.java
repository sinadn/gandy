package com.example.gandy.payload.response;

import com.example.gandy.entity.AttributeType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttributeTypeResponse {
        private AttributeType item;
        private List<AttributeOptionResponse> list;

}
