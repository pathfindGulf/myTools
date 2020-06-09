package com.mlk.tools;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

/**
 *  file 文件工具类
 */
@Component
public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     *  file 转 MultipartFile ;供feign调用ftp时使用
     * @param file 要转的file文件
     *@return org.springframework.web.multipart.MultipartFile
     */
    public static MultipartFile fileToMultipartFile(File file){
        DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("file",
                MediaType.TEXT_PLAIN_VALUE, true, file.getName());
        try (InputStream input = new FileInputStream(file); OutputStream os = fileItem.getOutputStream()) {
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }
        MultipartFile multi = new CommonsMultipartFile(fileItem);
        return multi;
    }

    //静态方法：三个参数：文件的二进制，文件路径，文件名
    //通过该方法将在指定目录下添加指定文件
    public static void fileupload(byte[] file,String filePath,String fileName) throws IOException {
        //目标目录
        File targetfile = new File(filePath);
        if(!targetfile.exists()) {
            targetfile.mkdirs();
        }
        //二进制流写入
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static boolean checkFileWritingOn(String fileName) {
        int wait = 0;
        int limit = 1; //超时期限
        File file = new File(fileName);
        while(true){
            if (file.exists() || wait >= limit) {
                return true;
            } else {
                System.out.println("文件暂不可读取-" + fileName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                wait ++ ;
            }
        }
    }

}
