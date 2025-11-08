package com.example.gandy.utility;

import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;

public class NameBuilder {

    public String convertor(MultipartFile file) {
        String extension = null;
        String fName = "";
        java.util.Date dt = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        fName = fmt.format(dt);
        String f = file.getOriginalFilename();
// Extract the extension from the file name
        int index = f.lastIndexOf('.');
        if (index > 0) {
            extension = "."+f.substring(index+1);
            System.out.println(extension);
        }
        return fName+extension;
    }
}
