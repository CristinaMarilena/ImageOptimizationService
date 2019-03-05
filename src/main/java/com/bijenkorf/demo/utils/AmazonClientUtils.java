package com.bijenkorf.demo.utils;

import org.apache.commons.io.FilenameUtils;

public class AmazonClientUtils {

    public static String getDirectoryPathOfImage(String predefinedType, String filename) {

        StringBuilder pathBuilder = new StringBuilder();

        final String extension = FilenameUtils.getExtension(filename);
        String filenameWithoutExtension = FilenameUtils.removeExtension(filename);
        filenameWithoutExtension = filenameWithoutExtension.replace("/","_");

        pathBuilder.append("/").append(predefinedType).append("/");

        if (filenameWithoutExtension.length() >= 4) {
            pathBuilder.append(filenameWithoutExtension, 0, 4).append("/");
        }
        if (filenameWithoutExtension.length() >= 8) {
            pathBuilder.append(filenameWithoutExtension, 4, 8).append("/");
        }
        pathBuilder.append(filenameWithoutExtension);

        return pathBuilder
                .append(".")
                .append(extension)
                .toString();
    }
}
