package com.example.myproject.controllers;

import com.example.myproject.Service.ImageService;
import com.example.myproject.entities.Image;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@AllArgsConstructor
//@RequestMapping("/imageUpload")
//@CrossOrigin(origins = "http://localhost:8082") open for specific port
@CrossOrigin() // open for all ports
@RequestMapping("/image")
public class ImageController {

    ImageService iimageService;

    @PutMapping
    public ResponseEntity<ImageUploadResponse> uplaodImage(@RequestParam("image") MultipartFile file)
            throws IOException {

        return   iimageService.uplaodImage(file);
    }

    @GetMapping(path = {"/get/info/{name}"})
    public Image getImageDetails(@PathVariable("name") String name) throws IOException {

        return iimageService.getImageDetails(name);
    }

    @GetMapping(path = {"/get/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {

        return iimageService.getImage(name);
    }

}
