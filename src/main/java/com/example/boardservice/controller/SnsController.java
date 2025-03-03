package com.example.boardservice.controller;

import com.example.boardservice.config.AwsConfig;
import com.example.boardservice.service.SnsService;
import com.example.boardservice.service.impl.SlackService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.Map;

@Log4j2
@RestController
public class SnsController {

    private final AwsConfig awsConfig;
    private final SnsService snsService;
    private final SlackService slackService;

    public SnsController(AwsConfig awsConfig, SnsService snsService, SlackService slackService) {
        this.awsConfig = awsConfig;
        this.snsService = snsService;
        this.slackService = slackService;
    }

    @PostMapping("/create-topic")
    public ResponseEntity<String> createTopic(@RequestParam final String topicName) {
        final CreateTopicRequest createTopicRequest = CreateTopicRequest.builder()
                .name(topicName)
                .build();

        SnsClient snsClient = snsService.getSnsClient();
        final CreateTopicResponse createTopicResponse = snsClient.createTopic(createTopicRequest);

        if(!createTopicResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(createTopicResponse);
        }
        log.info("topic name = " + createTopicResponse.topicArn());
        snsClient.close();
        return new ResponseEntity<>("TOPIC CREATING SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam final String endpoint, @RequestParam final String topicArn) {
        final SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol("https")
                .topicArn(topicArn)
                .endpoint(endpoint)
                .build();

        SnsClient snsClient = snsService.getSnsClient();
        final SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);

        if(!subscribeResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(subscribeResponse);
        }

        log.info("topicARN to subsribe = " + subscribeResponse.subscriptionArn());
        snsClient.close();
        return new ResponseEntity<>("SUBSCRIBE SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/publish")
    public String publish(@RequestParam String topicArn, @RequestBody Map<String, Object> message) {
        SnsClient snsClient = snsService.getSnsClient();
        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .subject("HTTP ENDPOINT TEST MESSAGE")
                .message(message.toString())
                .build();

        PublishResponse publishResponse = snsClient.publish(publishRequest);
        log.info("message : " + publishResponse.sdkHttpResponse().statusCode());
        snsClient.close();
        return publishResponse.messageId();
    }


    private ResponseStatusException getResponseStatusException(SnsResponse snsResponse) {
        return new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                snsResponse.sdkHttpResponse().statusText().get()
        );
    }

    // slack
    @GetMapping("/slack/error")
    public void error() {
        slackService.sendSlackMessage("패스트캠퍼스 게시판 프로젝트 슬랙 에러 테스트", "error");
    }

}
