package com.aws.example.tests3.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties//(prefix = "aws")
public class AwsProperties {
	private S3Properties s3Properties = new S3Properties();
	private ClientProperties clientProperties = new ClientProperties();

	public S3Properties getS3Properties() {
		return s3Properties;
	}

	public void setS3Properties(S3Properties s3Properties) {
		this.s3Properties = s3Properties;
	}

	public ClientProperties getClientProperties() {
		return clientProperties;
	}

	public void setClientProperties(ClientProperties clientProperties) {
		this.clientProperties = clientProperties;
	}

}
