package com.beimin.eveapi.corporation.starbase.detail;


import com.beimin.eveapi.core.AbstractApiParser;
import com.beimin.eveapi.core.ApiAuth;
import com.beimin.eveapi.core.AbstractContentHandler;
import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.core.ApiPage;
import com.beimin.eveapi.core.ApiPath;

public class StarbaseDetailParser extends AbstractApiParser<StarbaseDetailResponse> {
	public StarbaseDetailParser() {
		super(StarbaseDetailResponse.class, 2, ApiPath.CORPORATION, ApiPage.STARBASE_DETAIL);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new StarbaseDetailHandler();
	}

	public static StarbaseDetailParser getInstance() {
		return new StarbaseDetailParser();
	}

	public StarbaseDetailResponse getResponse(ApiAuth<?> auth, long itemID) throws ApiException {
		return super.getResponse(auth, "itemID", Long.toString(itemID));
	}
}
