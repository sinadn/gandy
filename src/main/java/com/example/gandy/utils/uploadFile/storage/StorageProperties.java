package com.example.gandy.utils.uploadFile.storage;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {
    private String location = "c://images";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
            this.location = location;
    }

}