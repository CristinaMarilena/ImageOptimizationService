package com.bijenkorf.demo.service.amazon;

import com.amazonaws.services.s3.model.S3Object;

import java.io.File;

public interface S3Service {

    String getPathOfObjectInBucket(String imageType, String filename);
    S3Object downloadFile(String keyName);
    void uploadFile(String filename, File file);
}
