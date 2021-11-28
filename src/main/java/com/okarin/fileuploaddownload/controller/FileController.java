package com.okarin.fileuploaddownload.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static java.nio.file.Files.copy;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Paths.get;

@RestController
@RequestMapping("/file")
public class FileController {

   //define a location
    public static final String DIRECTORY=System.getProperty("user.home")+"/Downloads/uploads/";


    //Define a method to upload files
    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles){
        List<String> filenames=new ArrayList<>();
        for(MultipartFile file:multipartFiles){
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage=get(DIRECTORY,filename).toAbsolutePath().normalize();
            try {
                copy(file.getInputStream(),fileStorage,REPLACE_EXISTING);
                filenames.add(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok().body(filenames);

    }

    //Define a method to download files


}
