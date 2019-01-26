package com.wteam.ug.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * filePath:自己的静态资源路径,比如 D:/IDEA/projects/ug/src/main/resources/static/
 * definePath:取决于file的类型,图片存: image/ 视频存: video/
 */
public class UploadUtils2 {
    private UploadUtils2(){}

    /**
     * 单文件上传
     * @param file
     * @param filePath
     * @param definePath
     * @return
     */
    public static String[] uploadFile(MultipartFile file,String filePath,String definePath){
        String[] message = new String[2];
//        message[0]:存放上传信息,message[1]:存放上传文件的路径,将路径存入数据表中,前端即可通过该路径找到该文件资源
        try {
            if (file.isEmpty()) {
                message[0] = "文件为空";
                return message;
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffix = getSuffix(fileName);
            System.out.println("文件的后缀名为：" + suffix);
            // 设置文件存储路径
            String upload = definePath + getFileName(fileName);
            String path = filePath + upload;
//          例如 /image/sgfhwquhwujhruwufsfsghuhuq.jpg
//          upload 是前端访问资源的路径,可以通过 http://localhost:8080/image/sgfhwquhwujhruwufsfsghuhuq.jpg来访问
//          upload 存入数据表中
            message[1] = upload;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            message[0] = "上传成功!";
            return message;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        message[0] = "上传失败!";
        return message;
    }

    /**
     * 多文件上传
     * @param files
     * @param filePath
     * @param definePath
     * @return
     */
    public static String[] uploadFile(MultipartFile[] files,String filePath,String definePath){
        String[] urls = new String[files.length];
        for (int i = 0;i < files.length;i++){
            if(files[i] != null){
                try {
                    // 获取文件名
                    String fileName = files[i].getOriginalFilename();
                    System.out.println("上传的文件名为：" + fileName);
                    // 获取文件的后缀名
                    String suffix = getSuffix(fileName);
                    System.out.println("文件的后缀名为：" + suffix);
                    // 设置文件存储路径
                    String upload = definePath + getFileName(fileName);
                    String path = filePath + upload;
                    File dest = new File(path);
                    // 检测是否存在目录
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();// 新建文件夹
                    }
                    urls[i] = upload;
                    files[i].transferTo(dest);// 文件写入
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return urls;
    }

    /**
     * 得到文件前缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName){
        return getUUID() + getSuffix(fileOriginName);
    }

    /**
     * 得到UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
