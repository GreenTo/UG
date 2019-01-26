package com.wteam.ug.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

public class UploadUtils {

    private UploadUtils(){}
    public static String[] uploadFile(MultipartFile file){
        String[] message = new String[2];
        String upload = "D:/IDEA/projects/postman/src/main/resources/static/";
        boolean b = false;
        File toFile = new File("");
        if (!file.isEmpty()) {
            try {
                String suffix = StringUtils.substringAfter(file.getOriginalFilename(),".");
                String path;
                if(suffix.equals("jpg") || suffix.equals("png") || suffix.equals("gif")){
                    b = true;
                    File fromPic = new File(upload+"/1.jpg");
                    FileUtils.copyInputStreamToFile(file.getInputStream(), fromPic);
                    path = "/download/image/" + UUID.randomUUID().toString().replace("-","") + "." + suffix;
                    File toPic = new File(upload + path);
                    Thumbnails.of(fromPic).size(400,500).toFile(toPic);
                    FileUtils.forceDelete(fromPic);
                    message[1] = path;
                }else if (suffix.equals("txt") || suffix.equals("html") || suffix.equals("css") || suffix.equals("js") || suffix.equals("sql")
                || suffix.equals("c") || suffix.equals("asm") || suffix.equals("doc") || suffix.equals("ppt")){
                    path = "/download/document/" + UUID.randomUUID().toString().replace("-","") + "." + suffix;
                    toFile = new File(upload + path);
                }else {
                    path = "/download/video/" + UUID.randomUUID().toString().replace("-","") + "." + suffix;
                    toFile = new File(upload + path);
                }
                if (b == false){
                    BufferedOutputStream out = new BufferedOutputStream(
                            new FileOutputStream(toFile));
                    System.out.println(file.getName());
                    out.write(file.getBytes());
                    out.flush();
                    out.close();
                    message[1] = path;
                }
                System.out.println("path:" + message[1]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                message[0] = "上传失败!";
                return message;
            } catch (IOException e) {
                e.printStackTrace();
                message[0] = "上传失败!";
                return message;
            }
            message[0] = "上传成功!";
            return message;
        } else {
            message[0] = "上传失败，因为文件是空的.";
            return message;
        }
    }
}
