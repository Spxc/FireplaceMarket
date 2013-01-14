package com.fireplace.market.fads.rest;

import java.util.HashMap;

import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;

import com.fireplace.market.fads.Fireplace;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FireplaceRestTemplate extends RestTemplate {

	private static Gson mGson = new GsonBuilder().setDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS").create();

	public FireplaceRestTemplate(String versionedAPIroot, Context context) {
		this(versionedAPIroot, mGson, context);
	}

	public FireplaceRestTemplate(String versionedAPIroot, Context context,
			Gson gson) {
		this(versionedAPIroot, gson, context);
	}

	private FireplaceRestTemplate(String versionedAPIroot, Gson gson,
			Context context) {
		ApiRoot = versionedAPIroot;
		GsonHttpMessageConverter convertor = (gson != null) ? new GsonHttpMessageConverter(
				gson) : new GsonHttpMessageConverter();
		getMessageConverters().add(convertor);
		setErrorHandler(new FireplaceResponseErrorHandler<ErrorMessage>(
				ErrorMessage.class, context));
	}

	public <T> FireplaceRestTemplate(String versionedAPIroot,
			HttpMessageConverter<T> messageConverter, Context context) {
		ApiRoot = versionedAPIroot;
		getMessageConverters().add(messageConverter);
		setErrorHandler(new FireplaceResponseErrorHandler<ErrorMessage>(
				ErrorMessage.class, context));
	}

	private static String ApiRoot;
	private static final String TAG = FireplaceRestTemplate.class
			.getSimpleName();

	/**
	 * @param resource
	 *            The collection name.
	 * @param resourceId
	 *            This is id of the resource to delete.
	 * @throws RestResponseException
	 */
	public void deleteRESTful(String resource, int resourceId)
			throws RestResponseException {
		doRESTful(HttpMethod.DELETE, resource, null, null, null, resourceId);
	}

	/**
	 * @param resource
	 *            The collection name.
	 * @param resourceId
	 *            This is id of the resource to delete.
	 * @throws RestResponseException
	 */
	public void deleteRESTful(String resource, String resourceId)
			throws RestResponseException {
		doRESTful(HttpMethod.DELETE, resource, null, null, null, resourceId);
	}

	/**
	 * 
	 * @param resource
	 *            The collection name.
	 * @param requestObject
	 *            The object needed to communicate with the rest api.
	 * @param mapValues
	 *            queryParmaeters.
	 * @throws RestResponseException
	 *             will be thrown if unexpected results are to be returned.
	 */
	public void putRESTful(String resource, Object requestObject,
			HashMap<String, ?> mapValues) throws RestResponseException {
		doRESTful(HttpMethod.PUT, resource, requestObject, null, mapValues, "");
	}

	/**
	 * 
	 * @param resource
	 *            The collection name.
	 * @param requestObject
	 *            The object needed to communicate with the rest api.
	 * @throws RestResponseException
	 *             will be thrown if unexpected results are to be returned.
	 */
	public void putRESTful(String resource, Object requestObject)
			throws RestResponseException {
		doRESTful(HttpMethod.PUT, resource, requestObject, null, null, "");
	}

	/**
	 * 
	 * @param resource
	 *            The collection name.
	 * @param responseType
	 *            The class that will define the return type.
	 * @param mapValues
	 *            queryParmaeters.
	 * @param resourceId
	 *            The identifier of an item in the collection.
	 * @return The requested object.
	 * @throws RestResponseException
	 *             will be thrown if unexpected results are to be returned.
	 */
	public <T> T getRESTful(String resource, Class<T> responseType,
			HashMap<String, ?> mapValues, int resourceId)
			throws RestResponseException {
		return doRESTful(HttpMethod.GET, resource, null, responseType,
				mapValues, resourceId);
	}

	/**
	 * 
	 * @param resource
	 *            The collection name.
	 * @param responseType
	 *            The class that will define the return type.
	 * @param resourceId
	 *            The identifier of an item in the collection.
	 * @return The requested object.
	 * @throws RestResponseException
	 *             will be thrown if unexpected results are to be returned.
	 */
	public <T> T getRESTful(String resource, Class<T> responseType,
			int resourceId) throws RestResponseException {
		return doRESTful(HttpMethod.GET, resource, null, responseType, null,
				resourceId);
	}

	/**
	 * 
	 * @param resource
	 *            The collection name.
	 * @param responseType
	 *            The class that will define the return type.
	 * @return The requested collection.
	 * @throws RestResponseException
	 *             will be thrown if unexpected results are to be returned.
	 */
	public <T> T getRESTful(String resource, Class<T> responseType)
			throws RestResponseException {
		return doRESTful(HttpMethod.GET, resource, null, responseType, null, "");
	}

	/**
	 * 
	 * @param resource
	 *            The collection name.
	 * @param responseType
	 *            The class that will define the return type.
	 * @param mapValues
	 *            queryParmaeters.
	 * @return The requested collection.
	 * @throws RestResponseException
	 *             will be thrown if unexpected results are to be returned.
	 */
	public <T> T getRESTfulSearch(String resource, Class<T> responseType,
			HashMap<String, ?> mapValues) throws RestResponseException {
		return getForObject(ApiRoot + resource, responseType, mapValues);
	}

	/**
	 * 
	 * @param resource
	 *            The collection name.
	 * @param requestObject
	 *            The object needed to communicate with the rest api.
	 * @param responseType
	 *            The class that will define the return type.
	 * @return The newly created object.
	 * @throws RestResponseException
	 *             will be thrown if unexpected results are to be returned.
	 */
	public <T> T postRESTful(String resource, Object requestObject,
			Class<T> responseType) throws RestResponseException {
		return doRESTful(HttpMethod.POST, resource, requestObject,
				responseType, null, "");
	}

	/**
	 * 
	 * @param resource
	 *            The collection name.
	 * @param requestObject
	 *            The object needed to communicate with the rest api.
	 * @param responseType
	 *            The class that will define the return type.
	 * @param mapValues
	 *            queryParmaeters.
	 * @return The newly created object.
	 * @throws RestResponseException
	 *             will be thrown if unexpected results are to be returned.
	 */
	public <T> T postRESTful(String resource, Object requestObject,
			Class<T> responseType, HashMap<String, ?> mapValues)
			throws RestResponseException {
		return doRESTful(HttpMethod.POST, resource, requestObject,
				responseType, mapValues, "");
	}

	/**
	 * 
	 * @param verb
	 *            The HttpMethod that is desired.
	 * @param resource
	 *            The collection name.
	 * @param requestObject
	 *            The object needed to communicate with the rest api.
	 * @param responseType
	 *            The class that will define the return type.
	 * @param mapValues
	 *            These are optional, and may be null.
	 * @param resourceId
	 *            This is only required for a delete method, and optional on
	 *            gets and puts.
	 * @return The newly created object in the case of a post, the requested
	 *         object from a get. You will receive null in the case of put or
	 *         delete, and this is expected. A RestResponseException will be
	 *         thrown if desired from the server; This will need to be handled.
	 * @throws RestResponseException
	 */
	public <T> T doRESTful(HttpMethod verb, String resource,
			Object requestObject, Class<T> responseType,
			HashMap<String, ?> mapValues, Object resourceId)
			throws RestResponseException {

		resource = ApiRoot
				+ resource
				+ ((resourceId != null && "" != resourceId) ? "/" + resourceId
						: "");
		Fireplace.debugLog(TAG, verb.name() + " >>> " + resource);
		T response = null;

		switch (verb) {
		case POST:

			response = (mapValues == null) ? postForObject(resource,
					requestObject, responseType) : postForObject(resource,
					requestObject, responseType, mapValues);

			break;
		case GET:

			response = (mapValues == null) ? getForObject(resource,
					responseType) : getForObject(resource, responseType,
					mapValues);

			break;

		case PUT:

			if (mapValues == null) {
				put(resource, requestObject);
			} else {
				put(resource, requestObject, mapValues);
			}

			break;

		case DELETE:

			delete(resource);

			break;

		default:
			break;
		}

		return response;
	}

}
