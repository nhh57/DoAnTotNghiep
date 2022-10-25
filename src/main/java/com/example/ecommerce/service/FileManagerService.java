package com.example.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface FileManagerService {
    byte[] read(String folder, String filename);
    List<String> save(String folder, MultipartFile[] files);
    void delete(String folder, String filename);
    List<String> list(String folder);
}
