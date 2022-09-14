package com.demo.stock;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response implements Serializable {

	private Data[] data;
	private String message;
	private int status;

	public Response() {
	}

	public Data[] getData() {
		return data;
	}

	public void setData(Data[] data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}	

	@Override
	public String toString() {
		return "{" + "data=" + Arrays.toString(data) + ", message='" + message + 
				"\', status=" + status + '}';
	}
}


