package com.yeming.site.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author yeming.gao
 * @Description: 文件工具类
 * @date 2019/6/28 16:40
 */
public class FileUtils {
    private FileUtils(){}

    /**
     * 读取文件类容
     * @param filePath 文件路径
     * @return String
     * @throws IOException 异常
     */
    public static String getFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream inputStream =new FileInputStream(file);
        byte[] filecontent =  new byte[inputStream.available()];
        inputStream.read(filecontent);
        inputStream.close();
        return new String(filecontent, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws IOException {
        String a = getFileContent("C:/Users/yeming.gao/Desktop/REPLY_CONTENT.ftl");
        System.out.println(a);
    }


}
