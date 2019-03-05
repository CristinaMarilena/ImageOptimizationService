# ImageOptimizationService

Code description will be provided here:

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




