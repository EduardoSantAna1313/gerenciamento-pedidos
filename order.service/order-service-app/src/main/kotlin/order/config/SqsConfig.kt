package order.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import java.net.URI

@Configuration
class SqsConfig {
    @Value("\${aws.sqs.queue-url}")
    private lateinit var queueUrl: String

    @Value("\${aws.sqs.region}")
    private lateinit var region: String

    @Value("\${aws.sqs.endpoint}")
    private lateinit var endpoint: String

    @Bean
    fun sqsClientLocal(): SqsClient {
        val awsCredentials = AwsBasicCredentials.create("test", "test")

        val builder = SqsClient.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .endpointOverride(URI.create(endpoint)) // Define o endpoint do LocalStack

        return builder.build()
    }
}