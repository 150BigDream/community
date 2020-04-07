package com.example.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public String upload(InputStream fileStream, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象。
        String generatedFilename;
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFilename = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, generatedFilename, fileStream);

            // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            ossClient.putObject(putObjectRequest);
//            Date expiration = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 365 * 10);1647184809000
            Long dadaa = 1962631209000L;
            Date expiration = new Date(dadaa);
            System.out.println(expiration);
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            URL url = ossClient.generatePresignedUrl(bucketName, generatedFilename, expiration);
            if (url != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
                return url.getPath();
            } else {
                throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
    }
}
