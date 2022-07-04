package com.test.fileupdown.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileResource {

    //define a location
    public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/upload/";

    //define a method upload files
    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : multipartFiles){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = Paths.get(DIRECTORY,fileName).toAbsolutePath().normalize();
            Files.copy(file.getInputStream(),fileStorage, StandardCopyOption.REPLACE_EXISTING);
            fileNames.add(fileName);
        }
        return ResponseEntity.ok().body(fileNames);
    }

    //define a method download files
}
