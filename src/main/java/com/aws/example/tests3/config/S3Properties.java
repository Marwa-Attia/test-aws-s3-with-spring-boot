package com.aws.example.tests3.config;

public class S3Properties {
	private String bucketName;
	private String keyArn;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getKeyArn() {
		return keyArn;
	}

	public void setKeyArn(String keyArn) {
		this.keyArn = keyArn;
	}

}
