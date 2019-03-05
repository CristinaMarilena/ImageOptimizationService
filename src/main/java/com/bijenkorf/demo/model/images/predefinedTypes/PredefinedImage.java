package com.bijenkorf.demo.model.images.predefinedTypes;

import com.bijenkorf.demo.model.images.Image;
import com.bijenkorf.demo.model.images.ScaleType;
import com.bijenkorf.demo.model.images.Type;
import com.bijenkorf.demo.edit.Optimizable;
import com.bijenkorf.demo.edit.Resizable;

public class PredefinedImage implements Resizable, Optimizable {

    private int height;
    private int width;
    private int quality;
    private ScaleType scaleType;
    private String fillColor;
    private Type type;

    public PredefinedImage() {
    }

    public PredefinedImage(int height, int width, int quality, ScaleType scaleType, String fillColor, Type type) {
        this.height = height;
        this.width = width;
        this.quality = quality;
        this.scaleType = scaleType;
        this.fillColor = fillColor;
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public Image optimize(Image image) { return null; }

    @Override
    public Image resize(Image image) {
        return null;
    }
}
