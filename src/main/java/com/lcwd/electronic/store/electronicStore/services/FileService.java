package com.lcwd.electronic.store.electronicStore.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    public String uploadFile(MultipartFile file, String path, String userId) throws IOException;
    public InputStream getResource(String path, String name) throws FileNotFoundException;
}
