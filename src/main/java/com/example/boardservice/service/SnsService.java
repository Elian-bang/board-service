package com.example.boardservice.service;

import com.example.boardservice.config.AwsConfig;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Service
public class SnsService {

    AwsConfig awsConfig;

    public SnsService(AwsConfig awsConfig) {
        this.awsConfig = awsConfig;
    }

    public AwsCredentialsProvider getAWSCredentials(String accessKeyId, String secretAccessKey) {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        return () -> awsBasicCredentials;
    }

    public SnsClient getSnsClient() {
        return SnsClient.builder()
                .credentialsProvider(getAWSCredentials(awsConfig.getAwsAccessKey(), awsConfig.getAwsSecretKey()))
                .region(Region.of(awsConfig.getAwsRegion()))
                .build();
    }
}
