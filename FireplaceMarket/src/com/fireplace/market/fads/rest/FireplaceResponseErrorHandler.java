package com.fireplace.market.fads.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResponseErrorHandler;

import android.content.Context;

public class FireplaceResponseErrorHandler<T> implements ResponseErrorHandler {

	private final List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

	private final HttpMessageConverterExtractor<T> delegate;

	public FireplaceResponseErrorHandler(Class<T> responseType, Context context) {
		// Set up the message Converters
		this.messageConverters.add(new ByteArrayHttpMessageConverter());
		this.messageConverters.add(new StringHttpMessageConverter());
		this.messageConverters.add(new ResourceHttpMessageConverter());
		this.messageConverters.add(new FormHttpMessageConverter());
		this.messageConverters.add(new GsonHttpMessageConverter());
		this.messageConverters.add(new SourceHttpMessageConverter());
		this.delegate = new HttpMessageConverterExtractor<T>(responseType,
				this.messageConverters);
	}

	// If a 300-500 series error is returned then we want to handle the error,
	// otherwise not
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		if (response != null && response.getStatusCode() != null) {
			HttpStatus statusCode = response.getStatusCode();
			if (statusCode.series() == Series.REDIRECTION
					|| statusCode.series() == Series.CLIENT_ERROR
					|| statusCode.series() == Series.SERVER_ERROR)
				return true;
		}
		return false;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		// Create a new generic Response Entity adding the unmarshalled
		// response, headers and status code
		ResponseEntity<T> responseEntity = new ResponseEntity<T>(
				this.delegate.extractData(response), response.getHeaders(),
				response.getStatusCode());

		// Create a new RestResponseException and set the ResponseEntity
		RestResponseException responseException = new RestResponseException();
		responseException.setResponseEntity(responseEntity);
		// Throw the Exception
		throw responseException;
	}

}