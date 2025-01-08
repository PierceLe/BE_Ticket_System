package com.scaffold.spring_boot.utils;

import org.springframework.stereotype.Service;

@Service
public class UploadFileUtils {
    public boolean isValidAvatarType(String fileType) {
        return fileType.equalsIgnoreCase("image/jpeg")
                || // For jpg and jpeg
                fileType.equalsIgnoreCase("image/png")
                || fileType.equalsIgnoreCase("image/svg+xml");
    }

    public boolean isValidAttachmentType(String fileType) {
        return fileType.equalsIgnoreCase("image/jpeg") // For jpg and jpeg
                || fileType.equalsIgnoreCase("image/png")
                || fileType.equalsIgnoreCase("image/svg+xml")
                || fileType.equalsIgnoreCase("application/pdf") // For PDF
                || fileType.equalsIgnoreCase("application/msword") // For DOC
                || fileType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document") // For DOCX
                || fileType.equalsIgnoreCase("text/plain"); // For TXT
    }
}
