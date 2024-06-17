package com.poscodx.jblog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

@Service
public class FileUploadService {
    private static String SAVE_PATH = "/Users/yerim/Desktop/jblog-uploads";
    private static String URL_PATH = "/assets/images";
    public String restore(MultipartFile file) {

        String url = null;
        try {
            File uploadDirectory = new File(SAVE_PATH);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            if (file.isEmpty()) {
                return url;
            }

            String originalFilename = file.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String saveFilename = generateSaveFiename(extName);
            long fileSize = file.getSize();

            System.out.println("originalFilename = " + originalFilename);
            System.out.println("saveFilename = " + saveFilename);
            System.out.println("fileSize = " + fileSize);

            byte[] data = file.getBytes();
            OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
            os.write(data);
            os.close();

            url = URL_PATH + "/" + saveFilename;

        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return url;
    }

    private String generateSaveFiename(String extName) {
        String filename = "";
        Calendar calendar = Calendar.getInstance();
        filename += calendar.get(Calendar.YEAR);
        filename += calendar.get(Calendar.MONTH);
        filename += calendar.get(Calendar.DATE);
        filename += calendar.get(Calendar.HOUR);
        filename += calendar.get(Calendar.MINUTE);
        filename += calendar.get(Calendar.SECOND);
        filename += calendar.get(Calendar.MILLISECOND);
        filename += ("." + extName);


        return filename;
    }
}
