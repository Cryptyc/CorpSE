package com.beimin.eveapi.corporation.member.security;


import com.beimin.eveapi.core.AbstractApiParser;
import com.beimin.eveapi.core.ApiAuth;
import com.beimin.eveapi.core.AbstractContentHandler;
import com.beimin.eveapi.core.ApiException;
import com.beimin.eveapi.core.ApiPage;
import com.beimin.eveapi.core.ApiPath;

public class MemberSecurityParser extends AbstractApiParser<MemberSecurityResponse> {
	public MemberSecurityParser() {
		super(MemberSecurityResponse.class, 2, ApiPath.CORPORATION, ApiPage.MEMBER_SECURITY);
	}

	@Override
	protected AbstractContentHandler getContentHandler() {
		return new MemberSecurityHandler();
	}

//	@Override
//	protected Digester getDigester() {
//		Digester digester = super.getDigester();
//		digester.addObjectCreate("eveapi/result/member", ApiSecurityMember.class);
//		digester.addSetProperties("eveapi/result/member");
//		digester.addFactoryCreate("eveapi/result/member/rowset", new AbstractObjectCreationFactory() {
//			@Override
//			public Object createObject(Attributes attributes) throws Exception {
//				String name = attributes.getValue("name");
//				if (name != null) {
//					SecurityRoleOrTitleBag roleBag = new SecurityRoleOrTitleBag();
//					roleBag.setName(name);
//					return roleBag;
//				}
//				return null;
//			}
//		});
//		digester.addObjectCreate("eveapi/result/member/rowset/row", SecurityRoleOrTitle.class);
//		digester.addSetProperties("eveapi/result/member/rowset/row");
//		digester.addSetNext("eveapi/result/member/rowset/row", "addSecurityRole");
//		digester.addSetNext("eveapi/result/member/rowset", "addSecurityRoleBag");
//		digester.addSetNext("eveapi/result/member", "addMember");
//		return digester;
//	}

	public static MemberSecurityParser getInstance() {
		return new MemberSecurityParser();
	}

	@Override
	public MemberSecurityResponse getResponse(ApiAuth<?> auth) throws ApiException {
		return super.getResponse(auth);
	}
}