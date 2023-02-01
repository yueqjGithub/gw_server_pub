package com.avalon.website.cat.component;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.UploadFileRequest;
import com.avalon.website.cat.exception.AvalonException;
import com.avalon.website.cat.http.AvalonError;
import com.avalon.website.cat.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadOss {

    public static void uploadFileStream (InputStream file, String objectName, String region, String keyId, String accessKeySecret, String bucket, String fileName, int times) throws IOException {
        // 保存文件
        FileOutputStream fos = new FileOutputStream(fileName);
        byte[] b = new byte[1024];
        while ((file.read(b)) != -1) {
            fos.write(b);
        }
        fos.close();

        OSS ossClient = new OSSClientBuilder().build(region, keyId, accessKeySecret);

        try {
            ObjectMetadata meta = new ObjectMetadata();
            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucket, objectName);
            uploadFileRequest.setUploadFile(fileName);
            uploadFileRequest.setTaskNum(5);
            uploadFileRequest.setPartSize(1024 * 1024);
            uploadFileRequest.setEnableCheckpoint(true);
            uploadFileRequest.setObjectMetadata(meta);
            ossClient.uploadFile(uploadFileRequest);
            FileUtils.deleteDir(fileName);
//            ossClient.putObject(bucket, objectName, file);
        } catch (OSSException oe) {
            throw new AvalonException(AvalonError.OSS_ERROR, "OSS上传失败");
        } catch (ClientException ce) {
            throw new AvalonException(AvalonError.OSS_ERROR, "OSS_Client出错");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new AvalonException(AvalonError.OSS_ERROR, "OSS_Client上传出错");
        } finally {
            if (times == 5) {
                FileUtils.deleteDir(fileName);
            }
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
