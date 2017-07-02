package com.creativeoh.keyword.common;

import lombok.Getter;
import lombok.Setter;

public final class KeywordDaum extends KeywordBaseAbs{
	@Getter @Setter private String url = "http://m.daum.net";
	@Getter @Setter private String selectorParent = ".list_issue.list_realtime li";
	@Getter @Setter private String selectorRank = ".num_issue";
	@Getter @Setter private String selectorKeyword = ".txt_issue";
	@Getter @Setter private String siteName = "DAUM";
		
	static KeywordDaum instance = null; 
	
	private KeywordDaum(){}
	
	public static KeywordDaum  getInstance(){
		if(instance == null)
			return new KeywordDaum();
		else
			return instance;
	}

	@Override
	public float getBasePoint() {
		return 3.3f;
	}
	
}
