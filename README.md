# ImageOptimizationService

Code description will be provided here:

The Image optimizations service provides the main enpoint

      ~/image/show/<predefined-type-name>/?reference=<unique-original-image-filename>
  
in *_ImageController_* class.

The process starts with a series of validation of the provided properties by the *_FileValidatorService_* which logs and throws the exception if any of them are not valid.

