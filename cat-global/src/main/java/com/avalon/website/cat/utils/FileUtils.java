package com.avalon.website.cat.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class FileUtils {
    /**
     * 读取某个路径下的文件
     * @param path
     */
    public static List<String> readDir (String path) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        List<String> nameList = new ArrayList<>(Collections.emptyList());
        if (tempList != null) {
            for (File fileItem : tempList) {
                nameList.add(fileItem.getName());
            }
        }
        return nameList;
    }
    /**
     * 删除某个路径下的文件
     * @param path
     */
    public static void deleteDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            log.info(path + "不存在");
        } else {
            if (file.isFile()) {
                if (file.delete()) {
                    log.info("删除" + path + "成功");
                } else {
                    log.info("删除" + path + "失败");
                }
            } else {
                List<File> fileList = Arrays.stream(Objects.requireNonNull(file.listFiles())).collect(Collectors.toList());
                if (fileList.size() == 0) {
                    if (file.delete()) {
                        log.info("删除" + path + "成功");
                    } else {
                        log.info("删除" + path + "失败");
                    }
                } else {
                    for (File fileItem : fileList) {
                        deleteDir(fileItem.getPath());
                    }
                    deleteDir(path);
                }
            }
        }
    }
    public static MultipartFile fileToMultipartFile (File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        return new MockMultipartFile(file.getName(), file.getName(),
                "text/plain", inputStream);
    }
    public static void byteFileToFile (MultipartFile file, String savePath) throws IOException {
        byte[] bytes = file.getBytes();
        FileOutputStream fos = new FileOutputStream(savePath);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(bytes);
    }
}
