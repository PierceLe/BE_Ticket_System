package com.scaffold.spring_boot.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.scaffold.spring_boot.exception.AppException;
import com.scaffold.spring_boot.exception.ErrorCode;

@Component
public class FileUtils {

    // Save file to a specific path
    public String saveFile(String filePath, MultipartFile file) {
        try {
            File destination = new File(filePath);
            file.transferTo(destination);
            return filePath;
        } catch (IOException e) {
            throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
        }
    }

    // Delete file from a specific path
    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile() && !file.delete()) {
            throw new AppException(ErrorCode.DELETE_FILE_ERROR);
        }
    }
}
