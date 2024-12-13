package com.scaffold.spring_boot.controller;

import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.service.FileDataServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileStorageController {
    private final FileDataServiceImpl fileDataService;

    @PostMapping("/file-upload-to-directory")
    public ResponseEntity<?> uploadImageToFileDirectory(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadFile = fileDataService.uploadFileToFileDirectory(file);

        return ResponseEntity.status(HttpStatus.OK).body(uploadFile);
    }

    @GetMapping("/file-download-from-directory/{fileName}")
    public ResponseEntity<?> downloadImageFromFileDirectory(@PathVariable String fileName) throws IOException{
        byte[] downloadFile = fileDataService.downloadFileFromFileDirectory(fileName);

        return ResponseEntity.status(HttpStatus.OK)
                .body(downloadFile);
    }
}
