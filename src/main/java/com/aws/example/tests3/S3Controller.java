package com.aws.example.tests3;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aws.example.tests3.service.ObjectService;
import com.aws.example.tests3.service.ObjectService.ObjectStreamHandler;

@RestController
@RequestMapping("/api/s3/buckets")
public class S3Controller {
	private ObjectService objectService;
	private Resource s3Object;

	@Autowired
	public S3Controller(ObjectService objectService) {
		this.objectService = objectService;
	}

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("hello");
	}

	@GetMapping("/{bucketName}/objects/{objectKey}")
	public ResponseEntity<Resource> downloadS3Object(@PathVariable String bucketName, @PathVariable String objectKey) {
		try {
			objectService.getObject(objectKey, new ObjectStreamHandler() {
				@Override
				public void handle(InputStream objectStream) throws IOException {
					s3Object = new InputStreamResource(objectStream);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(s3Object);
	}

	@PostMapping(path = "/{bucketName}/objects")
	public ResponseEntity<Void> uploadS3Object(@PathVariable String bucketName, @RequestParam("file") MultipartFile multipartFile)
			throws IOException, URISyntaxException {
		if (multipartFile.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		// final String objectKey =
		objectService.putObject(multipartFile.getOriginalFilename(), multipartFile.getResource().getInputStream());
		final URI location = new URI(String.format("%s/%s/objects/%s", "/api/s3/buckets", bucketName, multipartFile.getOriginalFilename()));
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{bucketName}/objects/{objectKey}")
	public ResponseEntity<Void> removeObject(@PathVariable String bucketName, @PathVariable String objectKey) {
		objectService.deleteObject(objectKey);
		return ResponseEntity.noContent().build();

	}
}
