package com.orange.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.exceptions.ClientException;
import com.orange.properties.AliyunOssProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@AllArgsConstructor
@Component
public class AliyunOSSOperator {
    private AliyunOssProperties aliyunOssProperties;

    public String upload(byte[] content, String objectName) throws Exception {
        String endpoint = aliyunOssProperties.getEndpoint();
        String bucketName = aliyunOssProperties.getBucketName();
        String region = aliyunOssProperties.getRegion();
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(content));
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            //ossClient.putObject(putObjectRequest);这行代码最重要不然白写了，或者说try里面就只要这个
            // ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content)
            System.out.println("上传成功: " + result.getETag());
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }

        }
        //这个是oss标准访问格式
        return endpoint.split("//")[0]+ "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
        //split分割生成字符串数组
    }
}