package com.bijenkorf.demo.controller;

import com.bijenkorf.demo.service.amazon.AmazonClientImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;

@RestController
@RequestMapping("/s3Bucket")
public class BucketController {

    @Value("${aws.bucket.name}")
    private String bucketName;

    private AmazonClientImageService amazonClientImageService;

    @Inject
    public BucketController(AmazonClientImageService amazonClientImageService, @Value("{$bucket.name}") final String bucketName){
        this.amazonClientImageService = amazonClientImageService;
        this.bucketName = bucketName;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) throws IOException {
        return this.amazonClientImageService.upload(file);
    }
}
