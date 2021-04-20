package com.sumana.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import java.io.IOException;

@Service
public class SecretService {

    private SecretsManagerClient secretsManagerClient;
    private ObjectMapper objectMapper;

    public SecretService(
            SecretsManagerClient secretsManagerClient,
            ObjectMapper objectMapper
    ) {
        this.secretsManagerClient = secretsManagerClient;
        this.objectMapper = objectMapper;
    }

    public JsonNode getSecretValue(String secretName) throws IOException {
        GetSecretValueRequest secretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        var result = secretsManagerClient.getSecretValue(secretValueRequest).secretString();

        return objectMapper.readValue(result, JsonNode.class);
    }
}
