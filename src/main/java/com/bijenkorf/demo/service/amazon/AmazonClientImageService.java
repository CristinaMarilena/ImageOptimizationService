package com.bijenkorf.demo.service.amazon;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.bijenkorf.demo.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;

import static com.bijenkorf.demo.utils.AmazonClientUtils.getDirectoryPathOfImage;
import static com.bijenkorf.demo.utils.FileUtils.convertMultiPartToFile;
import static com.bijenkorf.demo.utils.FileUtils.generateFileName;

@Service
public class AmazonClientImageService implements S3Service {

    private AmazonS3 s3Client;

    @Value("${aws.s3.endpoint}")
    private String endpointUrl;
    @Value("${aws.bucket.name}")
    private String bucketName;
    @Value("${aws.accesskey}")
    private String accessKey;
    @Value("${aws.secretkey}")
    private String secretKey;

    private Logger LOGGER = LoggerFactory.getLogger(AmazonClientImageService.class);

    @Inject
    public AmazonClientImageService(@Value("${aws.s3.endpoint}") String endpointUrl,
                                    @Value("${aws.bucket.name}") String bucketName,
                                    @Value("${aws.accesskey}") String accessKey,
                                    @Value("${aws.secretkey}") String secretKey,
                                    AmazonS3 s3Client) {
        this.endpointUrl = endpointUrl;
        this.bucketName = bucketName;
        this.secretKey = secretKey;
        this.accessKey = accessKey;
        this.s3Client = s3Client;
    }

    @PostConstruct
    private void initializeAmazonClient() {
        try {
            BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
            this.s3Client = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(creds)).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new NotFoundException(
                    "Cannot load the credentials. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location and the credentials are valid.");
        }
    }

    public String upload(MultipartFile multipartFile) {

        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFile(fileName, file);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }

        return fileUrl;
    }

    @Override
    public String getPathOfObjectInBucket(String imageType, String filename) {

        String directoryPathOfImage = getDirectoryPathOfImage(imageType, filename);

        try {
            if (s3Client.doesObjectExist(bucketName, directoryPathOfImage)) {
                return directoryPathOfImage;
            }
        } catch ( AmazonS3Exception e) {
            e.printStackTrace();
            throw new NotFoundException("An exception occured while checking if the object " + filename + " exists in the bucket");
        }

        return "";
    }

    @Override
    public S3Object downloadFile(String filePath) {
        S3Object s3object = new S3Object();

        try {
            s3object = this.s3Client.getObject(new GetObjectRequest(bucketName, filePath));
        } catch (AmazonServiceException e) {
            LOGGER.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            LOGGER.info("Error Message:    " + e.getMessage());
            LOGGER.info("HTTP Status Code: " + e.getStatusCode());
            LOGGER.info("AWS Error Code:   " + e.getErrorCode());
        } catch (AmazonClientException ace) {
            LOGGER.info("Caught an AmazonClientException: ");
            LOGGER.info("Error Message: " + ace.getMessage());
        }

        return s3object;
    }

    @Override
    public void uploadFile(String filename, File file) {
        this.s3Client.putObject(bucketName, filename, file);
    }
}