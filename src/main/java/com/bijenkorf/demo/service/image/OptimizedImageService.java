package com.bijenkorf.demo.service.image;

import com.bijenkorf.demo.model.images.Image;
import com.bijenkorf.demo.model.images.predefinedTypes.PredefinedImage;
import com.bijenkorf.demo.model.images.predefinedTypes.PredefinedImageFactory;
import com.bijenkorf.demo.service.amazon.S3Service;
import com.bijenkorf.demo.service.file.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;

import static com.bijenkorf.demo.utils.FileUtils.getFileFromImage;

@Service
public class OptimizedImageService {

    private S3Service s3Service;
    private FileService fileService;
    private Image image;

    private final String ORIGINAL_TYPE = "original";
    private String sourceRootUrl;

    @Inject
    public OptimizedImageService(S3Service s3Service, FileService fileService, @Value("${source-root-url}") String sourceRootUrl) {
        this.s3Service = s3Service;
        this.fileService = fileService;
        this.sourceRootUrl = sourceRootUrl;
    }

    public Image getOptimizedImage(String predefinedTypeName, String filename) {
        createOptimizedImage(predefinedTypeName, filename);
        return image;
    }

    private void createOptimizedImage(String predefinedTypeName, String filename) {

        String filePathInS3Bucket = s3Service.getPathOfObjectInBucket(predefinedTypeName, filename);

        if (filePathInS3Bucket.isEmpty()) {
            checkOriginalImage(predefinedTypeName, filename);
        } else {
            image = new Image(s3Service.downloadFile(filePathInS3Bucket).getObjectContent());
        }
    }

    private void checkOriginalImage(String predefinedTypeName, String filename) {

        PredefinedImage predefinedImage = PredefinedImageFactory.getPredefinedImage(predefinedTypeName);

        String filePath = s3Service.getPathOfObjectInBucket(ORIGINAL_TYPE, filename);

        if (filePath.isEmpty()) {
            uploadOriginalImageToS3(filePath);
            checkOriginalImage(predefinedTypeName, filename);
        } else {
            uploadEditedImageToS3(predefinedTypeName, filePath, predefinedImage);
            createOptimizedImage(predefinedTypeName, filename);
        }
    }

    private void uploadEditedImageToS3(String predefinedTypeName, String originalFilePath, PredefinedImage predefinedImage) {

        Image originalImage = new Image(s3Service.downloadFile(originalFilePath).getObjectContent());

        originalImage = editOriginalImage(originalImage, predefinedImage);

        File fileToUpload = getFileFromImage(originalImage, originalFilePath);
        String editedImageFilePath = originalFilePath.replace(ORIGINAL_TYPE, predefinedTypeName);

        s3Service.uploadFile(editedImageFilePath, fileToUpload);
    }

    private void uploadOriginalImageToS3(String filePath) {
        File file = fileService.getFileFromSystem(sourceRootUrl + filePath);
        s3Service.uploadFile(filePath, file);
    }

    private Image editOriginalImage(Image originalImage, PredefinedImage predefinedImage){
        originalImage = predefinedImage.resize(originalImage);
        originalImage = predefinedImage.optimize(originalImage);
        return originalImage;
    }
}
