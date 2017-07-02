package com.creativeoh.keyword.common;

import lombok.Getter;
import lombok.Setter;

public final class KeywordNaver extends KeywordBaseAbs{
	@Getter @Setter private String url = "http://www.naver.com";
	@Getter @Setter private String selectorParent = ".ah_roll_area .ah_item";
	@Getter @Setter private String selectorRank = ".ah_r";
	@Getter @Setter private String selectorKeyword = ".ah_k";
	@Getter @Setter private String siteName = "NAVER";
	@Getter @Setter private int sitePoint = 5;
	
	static KeywordNaver instance = null; 
	
	private KeywordNaver(){}
	
	public static KeywordNaver  getInstance(){
		if(instance == null)
			return new KeywordNaver();
		else
			return instance;
	}
	
	@Override
	public float getBasePoint() {
		return 5.5f;
	}
}
