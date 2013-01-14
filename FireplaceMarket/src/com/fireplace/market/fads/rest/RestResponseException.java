package com.fireplace.market.fads.rest;

import org.springframework.http.ResponseEntity;

public class RestResponseException extends RuntimeException {

	private static final long serialVersionUID = 8999059775307945887L;
	protected ResponseEntity<?> responseEntity;

	public RestResponseException() {
		super();
	}

	public ResponseEntity<?> getResponseEntity() {
		return this.responseEntity;
	}

	public void setResponseEntity(ResponseEntity<?> responseEntity) {
		this.responseEntity = responseEntity;
	}

}