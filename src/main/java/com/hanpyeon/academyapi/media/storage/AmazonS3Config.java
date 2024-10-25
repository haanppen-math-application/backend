package com.hanpyeon.academyapi.media.storage;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.awspring.cloud.autoconfigure.context.properties.AwsCredentialsProperties;
import io.awspring.cloud.core.io.s3.AmazonS3ClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AmazonS3Config {
    private final String accessKey;
    private final String secretKey;
    private final String region;

    public AmazonS3Config(@Value("${cloud.aws.credentials.access-key}") String accessKey, @Value("${cloud.aws.credentials.secret-key}") String secretKey, @Value("${cloud.aws.region.static}") String region) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return new AmazonS3Client(basicAWSCredentials);
    }
}
