package com.bijenkorf.demo.service;

import com.amazonaws.services.s3.model.S3Object;
import com.bijenkorf.demo.model.images.Image;
import com.bijenkorf.demo.service.amazon.S3Service;
import com.bijenkorf.demo.service.file.FileService;
import com.bijenkorf.demo.service.image.OptimizedImageService;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OptimizedImageServiceTest {

    private OptimizedImageService optimizedImageService;
    @Mock private S3Service s3Service;
    @Mock private FileService fileService;

    @Before
    public void setup(){
        optimizedImageService = new OptimizedImageService(s3Service, fileService, "/sourceRoot");
    }

    @Test
    public void getOptimizedImage__returns_image_when_file_is_already_optimized_and_on_path() throws IOException {
        String filename = "filename";
        S3Object s3Object = new S3Object();
        InputStream testInputStream = IOUtils.toInputStream("some test data for my input stream", "UTF-8");
        s3Object.setObjectContent(testInputStream);

        when(s3Service.getPathOfObjectInBucket(anyString(), anyString())).thenReturn("not_empty_path");
        when(s3Service.downloadFile(anyString())).thenReturn(s3Object);

        Image image = optimizedImageService.getOptimizedImage("thumbnail", filename);

        assertThat(s3Object.getObjectContent()).isEqualTo(image.getImageContent());
    }
}
