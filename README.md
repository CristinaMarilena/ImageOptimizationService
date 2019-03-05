# ImageOptimizationService

This is a partial implementation of an Image service that resizes and optimizes images and stores them in Amazon S3 bucket.

Code description will be provided here:

### The optimization process

The Image optimizations service provides the main enpoint

      ~/image/show/<predefined-type-name>/?reference=<unique-original-image-filename>
  
in *_ImageController_* class.

The process starts with a series of validation of the provided properties by the *_FileValidatorService_* which logs and throws the exception if any of them are not valid.

If the validation doesn't create any exceptions, the *_OptimizedImageService_* is called which implements the optimization process:

 - It all starts by calling the method which will look for the optimized file in s3Bucket using an implementation of S3Service(amazon s3 client service). 
  
     void createOptimizedImage(String predefinedTypeName, String filename) 

 - If the file is found it's being returned else it will start looking for the original image in s3Bucket.

    private void checkOriginalImage(String predefinedTypeName, String filename)
   
 - If the original file is there, then the image will be optimized and resized and then uploaded to s3Bucket under the predefined name type provided.

    private Image editOriginalImage(Image originalImage, PredefinedImage predefinedImage)

    private void uploadEditedImageToS3(String predefinedTypeName, String originalFilePath, PredefinedImage predefinedImage) 

- If the original file is not there, then it will be uploaded as an original in s3 bucket, after which the process will repeat itself so the image will be returned in an optimized and resized format.

    private void uploadOriginalImageToS3(String filePath) 
    
### The predefined types

I have created 2 predefined types which extend the original one called *_PredefinedImage_* which holds all the necessary properties. The original type will implement two interfaces called *_Resizable and Optimizable_* and it will provide the implementation. If the 2 predefined types need to provide other implementations for the methods the they can simply override them.

I have also created a predefined type factory class called *_PredefinedImageFactory_* which will create specific instance of a predefined type.

### Amazon client service

Unfortunately I couldn't get at this moment an active amazon s3 account so I can literally test the service but I have provided the main configuration for the client and basic methods which the Image service can use.

*_AmazonClientImageService_* implements S3Service and the basic methods of 

      String getPathOfObjectInBucket(String imageType, String filename);
      S3Object downloadFile(String keyName);
      void uploadFile(String filename, File file);
      
and also validates the amazon credentials and connects to the amazon service.

### Utility methods

A series of utility methods are being provided by classes in the *_utils_* directory, for working with files and the amazon client service.

### Tests

I have implemented in a very basic form and MVC test for ImageController, an unit test for AmazonClientUtils class and a service mocking test for OptimazedImageService.

The application fails to run because of invalid Amazon credentials but I do plan to make it fully functional because it's a great way for learning how to work with Amazon Services :)





    
    




