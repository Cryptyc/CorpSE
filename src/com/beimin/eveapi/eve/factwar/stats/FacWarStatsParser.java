package com.beimin.eveapi.eve.factwar.stats;


import com.beimin.eveapi.core.AbstractApiParser;
import com.beimin.eveapi.core.AbstractContentHandler;
import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.core.ApiPage;
import com.beimin.eveapi.core.ApiPath;

public class FacWarStatsParser extends AbstractApiParser<FacWarStatsResponse> {
	private FacWarStatsParser() {
		super(FacWarStatsResponse.class, 2, ApiPath.EVE, ApiPage.FACT_WAR_STATS);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new FacWarStatsHandler();
	}

	public static FacWarStatsParser getInstance() {
		return new FacWarStatsParser();
	}

	@Override
	public FacWarStatsResponse getResponse() throws ApiException {
		return super.getResponse();
	}
}