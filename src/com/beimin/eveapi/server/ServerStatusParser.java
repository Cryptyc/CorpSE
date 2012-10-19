package com.beimin.eveapi.server;


import com.beimin.eveapi.core.AbstractApiParser;
import com.beimin.eveapi.core.AbstractContentHandler;
import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.core.ApiPage;
import com.beimin.eveapi.core.ApiPath;

public class ServerStatusParser extends AbstractApiParser<ServerStatusResponse> {
	public ServerStatusParser() {
		super(ServerStatusResponse.class, 2, ApiPath.SERVER, ApiPage.SERVER_STATUS);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new ServerStatusHandler();
	}

	public static ServerStatusParser getInstance() {
		return new ServerStatusParser();
	}

	public ServerStatusResponse getServerStatus() throws ApiException {
		return getResponse();
	}
}