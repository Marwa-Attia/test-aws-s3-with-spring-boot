package com.aws.example.tests3.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.JsonMappingException;

import com.aws.example.tests3.model.DataItem;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileProcessorService {
	public final static int CHUNK_SIZE = 1000;
	private int currentObjectIndex = 0;
	private int currentChunkId = 1;
	private static final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) {
		FileProcessorService fileProcessorService = new FileProcessorService();
		fileProcessorService.parseJSON("C:/Marwa/work/aws/test.json");

	}

	public void parseJSON(String filename) {
		try {
			JsonFactory jsonfactory = new JsonFactory();
			File source = new File(filename);
			JsonParser parser = jsonfactory.createParser(source);
			// starting parsing of JSON String
			parser.nextToken();
			if (parser.isExpectedStartArrayToken()) {
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
				JsonToken token = parser.nextToken();

				while (token != JsonToken.END_ARRAY && token != null) {
					List<DataItem> items = new ArrayList<DataItem>();

					while (currentObjectIndex < CHUNK_SIZE && token != null) {
						DataItem dataItem = mapper.readValue(parser, DataItem.class);
						if (dataItem != null) {
							items.add(dataItem);
							currentObjectIndex++;
						} else {
							break;
						}
					}

					System.out.println("list " + currentChunkId + " " + items.size());
					token = parser.nextToken();
					System.out.println(token);
					currentChunkId++;
					currentObjectIndex = 0;

				}

			}

			parser.close();
		} catch (JsonGenerationException jge) {
			jge.printStackTrace();
		} catch (JsonMappingException jme) {
			jme.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

}
