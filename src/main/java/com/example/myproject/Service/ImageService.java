package com.example.myproject.Service;

import com.example.myproject.controllers.ImageUploadResponse;
import com.example.myproject.entities.Image;
import com.example.myproject.entities.User;
import com.example.myproject.repositories.ImageRepository;
import com.example.myproject.repositories.UserRepository;
import com.example.myproject.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Optional;
@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    UserRepository userDao;


    public ResponseEntity<ImageUploadResponse> uplaodImage(MultipartFile file)
            throws IOException {
        Image image1=new Image();
        image1.setImage(ImageUtility.compressImage(file.getBytes()));
        image1.setType(file.getContentType());
        image1.setName(file.getOriginalFilename());

       /* imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtility.compressImage(file.getBytes())).build());*/
        imageRepository.save(image1);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));


    }


    public Image getImageDetails(String name) throws IOException {


        final Optional<Image> dbImage = imageRepository.findByName(name);

        return Image.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .image(ImageUtility.decompressImage(dbImage.get().getImage())).build();
    }

    public ResponseEntity<byte[]> getImage(String name) throws IOException {


        final Optional<Image> dbImage = imageRepository.findByName(name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(ImageUtility.decompressImage(dbImage.get().getImage()));    }
}
