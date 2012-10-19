package com.beimin.eveapi.server;

import org.xml.sax.SAXException;

import com.beimin.eveapi.core.AbstractContentHandler;
import com.beimin.eveapi.core.ApiResponse;

public class ServerStatusHandler extends AbstractContentHandler {
	private ServerStatusResponse response;

	@Override
	public void startDocument() throws SAXException {
		response = new ServerStatusResponse();
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("serverOpen"))
			response.setServerOpen(getBoolean());
		else if (qName.equals("onlinePlayers"))
			response.setOnlinePlayers(getInt());
		super.endElement(uri, localName, qName);
	}

	@Override
	public ApiResponse getResponse() {
		return response;
	}
}