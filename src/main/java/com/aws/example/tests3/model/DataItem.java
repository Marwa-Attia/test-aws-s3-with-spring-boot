package com.aws.example.tests3.model;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude
public class DataItem {

	private String key;
	private String val;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	
}
