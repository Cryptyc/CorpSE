package com.beimin.eveapi.corporation.outpost.servicedetail;


import com.beimin.eveapi.core.AbstractListParser;
import com.beimin.eveapi.core.ApiAuth;
import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.core.ApiPage;
import com.beimin.eveapi.core.ApiPath;

public class OutpostServiceDetailParser extends AbstractListParser<OutpostServiceDetailHandler, OutpostServiceDetailResponse, ApiOutpostServiceDetail> {
	public OutpostServiceDetailParser() {
		super(OutpostServiceDetailResponse.class, 2, ApiPath.CORPORATION, ApiPage.OUTPOST_SERVICE_DETAIL, OutpostServiceDetailHandler.class);
	}

	public static OutpostServiceDetailParser getInstance() {
		return new OutpostServiceDetailParser();
	}

	@Override
	public OutpostServiceDetailResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}