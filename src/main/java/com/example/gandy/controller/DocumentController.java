package com.example.gandy.controller;//package com.example.gandymobile.controller;
//
//import com.example.gandymobile.service.DocumentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/pdf")
//public class DocumentController {
//
//    @Autowired
//    private DocumentService documentService;
//
//    @GetMapping("/download")
//
//    public ResponseEntity<InputStreamResource> generatePDF() throws IOException {
////        ByteArrayOutputStream wordOutputStream = documentService.generateWordWithTable();
////        BufferedImage image = documentService.convertWordToImage(wordOutputStream);
//        ByteArrayOutputStream pdfOutputStream = documentService.generatePDF();
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=table.pdf")
//                .contentType(MediaType.APPLICATION_PDF)
//                .contentLength(pdfOutputStream.size())
//                .body(new InputStreamResource(new ByteArrayInputStream(pdfOutputStream.toByteArray())));
//    }
//}
