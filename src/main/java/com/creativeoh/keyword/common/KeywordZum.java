package com.creativeoh.keyword.common;

import lombok.Getter;
import lombok.Setter;

public final class KeywordZum extends KeywordBaseAbs{
	@Getter @Setter private String url = "http://www.zum.com";
	@Getter @Setter private String selectorParent = ".d_rank_keyword_0 > li";
	@Getter @Setter private String selectorRank = "";
	@Getter @Setter private String selectorKeyword = ".d_ready span";
	@Getter @Setter private String siteName = "ZUM";
	@Getter @Setter private int sitePoint = 3;
	
	static KeywordZum instance = null; 
	
	private KeywordZum(){}
	
	public static KeywordZum  getInstance(){
		if(instance == null)
			return new KeywordZum();
		else
			return instance;
	}
	
	@Override
	public float getBasePoint() {
		return 1.1f;
	}
	
}
