package com.example.pastry.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface CloudinaryService {

    String uploadPicture(MultipartFile multipartFile) throws IOException;
}
