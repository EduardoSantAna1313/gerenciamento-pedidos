package br.com.edu.order.infra.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class SqsConfig {

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    @Value("${aws.sqs.region}")
    private String region;

    @Value("${aws.sqs.endpoint}")
    private String endpoint;

    @Bean
    public SqsClient sqsClientLocal() {
        final var awsCredentials = AwsBasicCredentials.create("test", "test");

        final var builder = SqsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .endpointOverride(URI.create(endpoint)); // Define o endpoint do LocalStack

        return builder.build();
    }

}
