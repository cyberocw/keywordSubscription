package com.creativeoh.keyword.batch.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.creativeoh.keyword.Keyword;
import com.creativeoh.keyword.api.dao.KeywordDao;
import com.creativeoh.keyword.common.Codes;
import com.creativeoh.keyword.common.KeywordBaseAbs;
import com.creativeoh.keyword.common.KeywordDaum;
import com.creativeoh.keyword.common.KeywordNaver;
import com.creativeoh.keyword.common.KeywordZum;
import com.creativeoh.keyword.common.util.Utils;

@Service
public class PortalInfoServiceImpl {
	@Autowired
	KeywordDao keywordDao;
	
	private String url;
	private String selectorParent;
	private String selectorRank;
	private String selectorKeyword;
	private String siteName;
	private int sitePoint;
	
	@Scheduled(cron = "0 0/30 * * * *")
	public void collectInsert() {
		System.out.println("collect insert start");
		KeywordBaseAbs nk = KeywordNaver.getInstance();
		ArrayList<Keyword> arrKeyword = new ArrayList();
		HashMap<String, Keyword> keywordMap = new HashMap();
		
		nk.collect(arrKeyword, keywordMap);

		nk = KeywordDaum.getInstance();
		nk.collect(arrKeyword, keywordMap);
		
		nk = KeywordZum.getInstance();
		nk.collect(arrKeyword, keywordMap);
		
		for(int i = 0 ; i < arrKeyword.size(); i++){
			Keyword helloData = keywordDao.save(arrKeyword.get(i));
		}
		
		long today = Utils.makeSimpleDateZero(new Date());
		//List<Keyword> todayList = keywordDao.findBySimpleDateGreaterThanEqual(today);
		
		List todayList = keywordDao.findBySimpleDateGroupGreaterThanEqual(Codes.KEYWORD_TYPE.BASE, today);
		
		
		ArrayList<Keyword> arr = new ArrayList<Keyword>();
		Date now = new Date();
		long simpleDate = Utils.makeSimpleDate(now);
		for(int i = 0; i < todayList.size(); i++){
			Object[] obj = (Object[]) todayList.get(i);
			Keyword vo = new Keyword();
			vo.setKeyword(obj[0].toString());
			vo.setPoint(Float.valueOf(obj[1].toString()));
			//vo.setFromSite(obj[2].toString());
			vo.setTypeCode(Codes.KEYWORD_TYPE.SUM);
			vo.setSimpleDate(simpleDate);
			vo.setRegDate(now);
			vo.setRank(i+1);
			arr.add(vo);
		}
		keywordDao.save(arr);

	}
}
 