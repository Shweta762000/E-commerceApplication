package com.lcwd.electronic.store.electronicStore.services.serviceImpl;

import com.lcwd.electronic.store.electronicStore.exceptions.BadApiRequest;
import com.lcwd.electronic.store.electronicStore.services.FileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    Logger logger= LoggerFactory.getLogger(FileService.class);
    @Override
    public String uploadFile(MultipartFile file, String path, String userId) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extention=originalFilename.substring(originalFilename.lastIndexOf("."));
        String newname= UUID.randomUUID().toString();
       String fileNameWithExtention=newname+extention;
       String fullPathwithFileName=path+fileNameWithExtention;
       logger.info("full image path is :{}",fullPathwithFileName);

        if(extention.equalsIgnoreCase(".png")||extention.equalsIgnoreCase(".jpg")||extention.equalsIgnoreCase("jpeg")){

            File folder=new File(path);
            if(!folder.exists()) folder.mkdirs();
            Files.copy(file.getInputStream(), Paths.get(fullPathwithFileName));
            return fileNameWithExtention;
        }else
        {
            throw new BadApiRequest("extention must be .png .jpg or jpeg");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
       String fullpath=path+name;
        return new FileInputStream(fullpath);
    }
}
