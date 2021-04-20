package com.sumana.demo.configuration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import java.io.IOException;
import java.net.URI;


@Configuration
@Profile("local")
public class SecretManagerLocalConfig {

    @Bean
    public SecretsManagerClient getSecretsManagerClient() {
        return SecretsManagerClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create("http://localhost:4566"))
                .build();
    }

    @Bean
    public JsonNode retrieveConfiguration(
            SecretsManagerClient secretsManagerClient,
            ObjectMapper objectMapper
    ) throws IOException {
        GetSecretValueRequest secretValueRequest = GetSecretValueRequest.builder()
                .secretId("my-secret")
                .build();

        var result = secretsManagerClient.getSecretValue(secretValueRequest).secretString();

        return objectMapper.readValue(result, JsonNode.class);
    }
}
