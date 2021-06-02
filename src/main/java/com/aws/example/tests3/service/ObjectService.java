package com.aws.example.tests3.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface ObjectService {
	void getObject(final String objectKey, final ObjectStreamHandler objectStreamHandler) throws IOException;

	void putObject(final String objectKey, final InputStream objecStream) throws IOException;

	void deleteObject(final String objectKey);

	Set<String> listObjects();

	interface ObjectStreamHandler {
		void handle(final InputStream objectStream) throws IOException;
	}
}
