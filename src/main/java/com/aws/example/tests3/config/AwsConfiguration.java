package com.aws.example.tests3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.aws.example.tests3.service.ObjectService;
import com.aws.example.tests3.service.S3Service;

//@Profile("aws")
@Configuration
public class AwsConfiguration {

	@Bean 
	public ObjectService objectService() {
        final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_2).build();
        final AwsProperties awsProperties= new AwsProperties();
        S3Properties properties= new S3Properties();
        properties.setBucketName("miro-test-bucket");
        awsProperties.setS3Properties(properties);
		return new S3Service(s3Client,awsProperties.getS3Properties().getBucketName());
	}
	
}
