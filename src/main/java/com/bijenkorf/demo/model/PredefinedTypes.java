package com.bijenkorf.demo.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PredefinedTypes {

    private List<String> imageTypes;

    public PredefinedTypes() {
        imageTypes = new ArrayList<>();
        imageTypes.add("thumbnail");
        imageTypes.add("detail-large");
    }

    public PredefinedTypes(List<String> imageTypes) {
        this.imageTypes = imageTypes;
    }

    public List<String> getImageTypes() {
        return imageTypes;
    }

    public void setImageTypes(List<String> imageTypes) {
        this.imageTypes = imageTypes;
    }

    public boolean addType(String newType){
        return imageTypes.add(newType);
    }

    public boolean removeType(String removedType){
        return imageTypes.remove(removedType);
    }

    public boolean containsImageType(String imageType){
        return imageTypes.contains(imageType);
    }
}
