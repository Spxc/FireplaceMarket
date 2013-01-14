package com.fireplace.market.fads.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestErrorWrapper {
	public RestErrorWrapper() {

	}

	public RestErrorWrapper(ResponseEntity<ErrorMessage> entity) {
		mStatus = entity.getStatusCode();
		mHeaders = entity.getHeaders();
		mErrorMessage = entity.getBody().getError();
	}

	private HttpStatus mStatus;
	private HttpHeaders mHeaders;
	private String mErrorMessage;

	public HttpStatus getStatus() {
		return mStatus;
	}

	public void setStatus(HttpStatus status) {
		this.mStatus = status;
	}

	public HttpHeaders getHeaders() {
		return mHeaders;
	}

	public void setHeaders(HttpHeaders headers) {
		this.mHeaders = headers;
	}

	public String getErrorMessage() {
		return mErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.mErrorMessage = errorMessage;
	}

}
