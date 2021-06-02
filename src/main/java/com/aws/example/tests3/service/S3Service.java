package com.aws.example.tests3.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Service implements ObjectService {

	private final AmazonS3 s3Client;
	private final String bucketName;

	public S3Service(AmazonS3 s3Client, String bucketName) {
		super();
		this.s3Client = s3Client;
		this.bucketName = bucketName;
	}

	@Override
	public void getObject(String objectKey, ObjectStreamHandler objectStreamHandler) throws IOException {
		S3Object o = s3Client.getObject(this.bucketName, objectKey);
		S3ObjectInputStream s3is = o.getObjectContent();
		objectStreamHandler.handle(s3is);
	}

	@Override
	public void putObject(String objectKey, InputStream objecStream) throws IOException {
		s3Client.putObject(this.bucketName,objectKey,  objecStream, null);
	}

	@Override
	public void deleteObject(String objectKey) {
		DeleteObjectsRequest dor = new DeleteObjectsRequest(this.bucketName).withKeys(objectKey);
		s3Client.deleteObjects(dor);

	}

	@Override
	public Set<String> listObjects() {
		ObjectListing listing = s3Client.listObjects(bucketName);
		final Set<String> objectKeys = new HashSet<String>(extractObjectKeys(listing));
		while (listing.isTruncated()) {
			listing = s3Client.listNextBatchOfObjects(listing);
			objectKeys.addAll(extractObjectKeys(listing));
		}
		return objectKeys;
	}

	private Set<String> extractObjectKeys(ObjectListing listing) {
		return listing.getObjectSummaries().stream().map(S3ObjectSummary::getKey).collect(Collectors.toSet());
	}

}
