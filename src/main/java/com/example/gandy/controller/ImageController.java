package com.example.gandy.controller;

import com.example.gandy.entity.Activation;
import com.example.gandy.service.ActivationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/img")
public class ImageController {

    @GetMapping("/{imgName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imgName) throws IOException {
        Path imagePath = Paths.get("C:/images/"+imgName);
        Resource imageResource = new FileSystemResource(imagePath);

        String lastThreeChars = imgName.substring(imgName.length() - 3);
        System.out.println(lastThreeChars);
        System.out.println(imageResource);
        if (lastThreeChars.equals("Svg") || lastThreeChars.equals("svg"))
            return ResponseEntity.ok().body(imageResource);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageResource);
    }
}
