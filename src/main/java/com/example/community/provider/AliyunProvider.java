package com.example.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class AliyunProvider {
    @Value("${aliyun.file.accesskey-id}")
    private String accessKeyId;

    @Value("${aliyun.file.accesskey-secret}")
    private String accessKeySecret;

    @Value("${aliyun.file.bucket-name}")
    private String bucketName;

    @Value("${aliyun.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.file.expiration}")
    private String expiration;

    public String upload(InputStream fileStream, String mimeType, String fileName) {
        String objectName;
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            objectName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }

        File file = new File(fileName);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ossClient.putObject(bucketName, objectName, file);
        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + 31536000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
        ossClient.shutdown();
        return url.getPath();
    }
}
