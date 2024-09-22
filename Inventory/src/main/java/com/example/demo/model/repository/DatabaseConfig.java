package com.example.demo.model.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DatabaseConfig {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.secret.name}")
    private String secretName;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;  // Get jdbcUrl from application.yml

    @Bean
    public DataSource dataSource() {
        // Create a Secrets Manager client
        SecretsManagerClient secretsClient = SecretsManagerClient.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(awsAccessKey, awsSecretKey)))
                .build();

        // Retrieve the secret value
        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();
        GetSecretValueResponse response = secretsClient.getSecretValue(request);

        // Parse the secret JSON
        String secret = response.secretString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> secretMap;
        try {
            secretMap = objectMapper.readValue(secret, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse secret JSON", e);
        }

        // Create the DataSource using the jdbcUrl from application.yml and secrets
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(jdbcUrl); // Use jdbcUrl from application.yml
        dataSource.setUsername(secretMap.get("username")); // Get username from secrets
        dataSource.setPassword(secretMap.get("password")); // Get password from secrets

        return dataSource;
    }
}
