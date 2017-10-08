package com.creativeoh.keyword.api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.creativeoh.keyword.Keyword;
import com.creativeoh.keyword.api.dao.KeywordDao;
import com.creativeoh.keyword.batch.service.PortalInfoServiceImpl;
import com.creativeoh.keyword.common.Codes;
import com.creativeoh.keyword.common.util.Utils;

@RestController
public class CollectController {
	@Autowired
	PortalInfoServiceImpl protalInfoService;
	
	@Autowired
	KeywordDao keywordDao;
	
	@RequestMapping("/collect")
	public List<Keyword> collect(){
		protalInfoService.collectInsert();
		
		//List<Keyword> helloList = keywordDao.findBySimpleDateGreaterThanEqual(today);
		long today = Utils.makeSimpleDateZero(new Date());
		List<Keyword> todayList = keywordDao.findBySimpleDateGroupGreaterThanEqual(Codes.KEYWORD_TYPE.BASE, today);
		
		return todayList;
	}
	
	@RequestMapping("/summary")
	public List<Keyword> summary(){
		//최신 날짜 구해온 다음 가져오기
		Keyword keyword = keywordDao.findTopByTypeCodeOrderBySimpleDateDesc(Codes.KEYWORD_TYPE.SUM);
		
		List<Keyword> todayList = null;
		if(keyword != null) {
		//처음것 가져옴
			todayList = keywordDao.findByTypeCodeAndSimpleDate(Codes.KEYWORD_TYPE.SUM, keyword.getSimpleDate());
		}
		return todayList;
	}
	
	@RequestMapping("/list")
	public List<Keyword> list(
			@RequestParam(value="mode") String mode,
			 @RequestParam(value="simpleDate") long simpleDate,
			 @RequestParam(value="typeCode", required=false) int typeCode,
			Model model
			) {

		List<Keyword> helloList = null;
		
		if(mode.equals("SUM")){
			helloList = protalInfoService.getSimpleDateSum(simpleDate);
		}
		else if(mode.equals("TIME")){
			helloList = keywordDao.findByTypeCodeAndSimpleDate(typeCode, simpleDate);
		}
		
		return helloList;
	}
	
	
	
	@RequestMapping("/add")
	public Keyword add(Keyword hello) {

		Keyword helloData = keywordDao.save(hello);
		return helloData;
	}
	
	
}
