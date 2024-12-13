package com.scaffold.spring_boot.service;

import com.scaffold.spring_boot.entity.FileData;
import com.scaffold.spring_boot.repository.FileDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileDataServiceImpl {
    @NonFinal
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/uploads/";
    private final FileDataRepository fileDataRepository;


    public String uploadFileToFileDirectory(MultipartFile file) throws IOException {
        String filePath = FILE_PATH+file.getOriginalFilename();//absolute path
        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build());

        file.transferTo(new java.io.File(filePath));

        return "file uploaded successfully : " + file.getOriginalFilename() + " and Files uploaded path is :" + filePath;
    }

    public byte[] downloadFileFromFileDirectory(String fileName) throws IOException {
        Optional<FileData> fileDataObj = fileDataRepository.findByName(fileName);
        //first need to get the file path
        String filePath = fileDataObj.get().getFilePath();
        //got the file, now decompress it.
        byte[] imageFile = Files.readAllBytes(new java.io.File(filePath).toPath());

        return imageFile;
    }

}
