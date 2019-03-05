package com.bijenkorf.demo.utils;

import com.bijenkorf.demo.model.images.Image;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.Date;

public class FileUtils {

    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public static String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    public static File getFileFromImage(Image image, String path){
        File file = new File(path);
        try {
            org.apache.commons.io.FileUtils.copyInputStreamToFile(image.getImageContent(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public static boolean checkIfFileIsAnImage(String filename){
        try {
            java.awt.Image image = ImageIO.read(new File(filename));
            if (image == null) {
            return false;
            }
        } catch(IOException ex) {
            return false;
        }

        return true;
    }

    public static boolean fileExists(String filename){
        File file = new File(filename);
        return file.isFile();
    }
}
