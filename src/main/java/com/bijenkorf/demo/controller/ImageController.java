package com.bijenkorf.demo.controller;

import com.bijenkorf.demo.model.images.Image;
import com.bijenkorf.demo.service.file.FileValidatorService;
import com.bijenkorf.demo.service.image.OptimizedImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/image")
public class ImageController {

    private OptimizedImageService optimizedImageService;
    private String sourceRootUrl;
    private FileValidatorService fileValidatorService;

    @Inject
    public ImageController(FileValidatorService fileValidatorService, OptimizedImageService optimizedImageService, @Value("${source-root-url}") String sourceRootUrl) {
        this.optimizedImageService = optimizedImageService;
        this.sourceRootUrl = sourceRootUrl;
        this.fileValidatorService = fileValidatorService;
    }

    @GetMapping("/show/{predefinedTypeName}")
    public ResponseEntity<Image> getImage(@PathVariable String predefinedTypeName,
                                          @RequestParam("reference") String reference) {

        validateImageProperties(predefinedTypeName, sourceRootUrl + reference);

        return new ResponseEntity<>(optimizedImageService.getOptimizedImage(predefinedTypeName, reference), HttpStatus.OK);
    }

    private void validateImageProperties(String predefinedTypeName, String reference){
        fileValidatorService.validatePredefinedTypeName(predefinedTypeName);
        fileValidatorService.validateIfFileExists(predefinedTypeName, reference);
        fileValidatorService.validateFileIsImage(reference);
    }
}
