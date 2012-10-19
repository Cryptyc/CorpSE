package com.beimin.eveapi.map.sovereignty;


import com.beimin.eveapi.core.AbstractListParser;
import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.core.ApiPage;
import com.beimin.eveapi.core.ApiPath;

public class SovereigntyParser extends AbstractListParser<SovereigntyHandler, SovereigntyResponse, ApiSystemSovereignty> {
	public SovereigntyParser() {
		super(SovereigntyResponse.class, 1, ApiPath.MAP, ApiPage.SOVEREIGNTY, SovereigntyHandler.class);
	}

	public static SovereigntyParser getInstance() {
		return new SovereigntyParser();
	}

	@Override
	public SovereigntyResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}