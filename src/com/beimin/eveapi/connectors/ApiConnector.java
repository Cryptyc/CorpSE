package com.beimin.eveapi.connectors;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.beimin.eveapi.core.ApiAuth;
import com.beimin.eveapi.core.AbstractContentHandler;
import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.core.ApiRequest;
import com.beimin.eveapi.core.ApiResponse;

public class ApiConnector {
	private static final Logger LOG = LoggerFactory.getLogger(ApiConnector.class);
	
	public static final String EVE_API_URL = "https://api.eveonline.com";
	private final String baseUrl;

	public ApiConnector() {
		baseUrl = EVE_API_URL;
	}

	public ApiConnector(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public <E extends ApiResponse> E execute(ApiRequest request, AbstractContentHandler contentHandler, Class<E> clazz) throws ApiException {
		try {
			return getApiResponse(contentHandler, getInputStream(getURL(request), getParams(request)), clazz);
		} catch (Exception e) {
			throw new ApiException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected <E> E getApiResponse(AbstractContentHandler contentHandler, InputStream inputStream, Class<E> clazz) throws ApiException {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance(); 
		    SAXParser sp = spf.newSAXParser(); 
		    XMLReader xr = sp.getXMLReader();
//		    System.out.println("Setting content handler" + contentHandler.getClass().getCanonicalName());
		    xr.setContentHandler(contentHandler);
//		    StringWriter writer = new StringWriter();
//		    IOUtils.copy(inputStream, writer);
//		    String theString = writer.toString();
//		    System.out.println(theString);
//		    InputStream savedInput = new ByteArrayInputStream(theString.getBytes());
		    xr.parse(new InputSource(inputStream));
			return (E) contentHandler.getResponse();
		} catch (Exception e) {
			throw new ApiException(e);
		}
	}

	protected InputStream getInputStream(URL requestUrl, Map<String, String> params) throws ApiException {
		OutputStreamWriter wr = null;
		try {
			URLConnection conn = openConnection(requestUrl);
			conn.setDoOutput(true);
			wr = new OutputStreamWriter(conn.getOutputStream());
			StringBuilder data = new StringBuilder();
			for (Entry<String, String> entry : params.entrySet()) {
				if (data.length() > 0) data.append("&"); // to ensure that we don't append an '&' to the end.
				String key = entry.getKey();
				String value = entry.getValue();
				data.append(URLEncoder.encode(key, "UTF8"));
				data.append("=");
				data.append(URLEncoder.encode(value, "UTF8"));
			}
			wr.write(data.toString());
			wr.flush();
			return conn.getInputStream();
		} catch (Exception e) {
			throw new ApiException(e);
		} finally {
			if (wr != null)
				try {
					wr.close();
				} catch (IOException e) {
					LOG.warn("Error closing the stream", e);
				}
		}
	}

	protected URLConnection openConnection(URL requestUrl) throws ApiException {
		try {
			return requestUrl.openConnection();
		} catch (Exception e) {
			throw new ApiException(e);
		}
	}

	protected URL getURL(ApiRequest request) throws ApiException {
		try {
			StringBuilder result = new StringBuilder(getBaseUrl());
			result.append(request.getPath().getPath());
			result.append("/").append(request.getPage().getPage());
			result.append(".xml.aspx");
			return new URL(result.toString());
		} catch (Exception e) {
			throw new ApiException(e);
		}
	}

	protected Map<String, String> getParams(ApiRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("version", Integer.toString(request.getVersion()));
		ApiAuth<?> auth = request.getAuth();
		if (auth != null)
			result.putAll(auth.getParams());
		Map<String, String> params = request.getParams();
		if (params != null) {
			result.putAll(params);
		}
		return result;
	}

	protected String getBaseUrl() {
		return baseUrl;
	}

	protected ApiConnector getConnector() {
		return this;
	}
}