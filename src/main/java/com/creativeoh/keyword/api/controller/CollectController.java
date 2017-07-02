package com.creativeoh.keyword.api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
		//protalInfoService.collectInsert();
		//List<Keyword> helloList = keywordDao.findBySimpleDateGreaterThanEqual(today);
		//long today = Utils.makeSimpleDateZero(new Date());
		
		Keyword keyword = keywordDao.findTopByTypeCodeOrderBySimpleDateDesc(Codes.KEYWORD_TYPE.SUM);
		
		System.out.println("keyword get =" + keyword.toString() );
		
		List<Keyword> todayList = keywordDao.findByTypeCodeAndSimpleDate(Codes.KEYWORD_TYPE.SUM, keyword.getSimpleDate());
		
		return todayList;
	}
	
	@RequestMapping("/list")
	public List<Keyword> list(Model model) {

		List<Keyword> helloList = keywordDao.findAll();
		//keywordDao.findB
		return helloList;
	}
	
	
	
	@RequestMapping("/add")
	public Keyword add(Keyword hello) {

		Keyword helloData = keywordDao.save(hello);
		return helloData;
	}
}
