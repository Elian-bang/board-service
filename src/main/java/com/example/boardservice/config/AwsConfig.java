package com.example.boardservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AwsConfig {

    @Value("${sns.topic.arn}")
    private String snsTopicArn;

    @Value("${sns.accessKey}")
    private String awsAccessKey;

    @Value("${sns.secretKey}")
    private String awsSecretKey;

    @Value("${sns.region}")
    private String awsRegion;

}
