package com.bijenkorf.demo.service.file;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public File getFileFromSystem(String path) {
       return new File(path);
    }
}
