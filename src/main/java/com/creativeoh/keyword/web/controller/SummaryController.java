package com.creativeoh.keyword.web.controller;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.creativeoh.keyword.Keyword;
import com.creativeoh.keyword.api.dao.KeywordDao;
import com.creativeoh.keyword.batch.service.PortalInfoServiceImpl;

@Controller
@RequestMapping("/web")
public class SummaryController {
	@Autowired
	PortalInfoServiceImpl protalInfoService;
	
	@Autowired
	KeywordDao keywordDao;

	@RequestMapping("/blogsummary")
	public String list(
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
		String url, keyword;
		for(int i = 0; i < helloList.size(); i++){
			Keyword vo = helloList.get(i);
			url = "";
			try {
                keyword = URLEncoder.encode(vo.getKeyword(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                keyword = vo.getKeyword();
            }
			
			int naver = 20, daum = 20, zum = 20;
            if(!StringUtils.isEmpty(vo.getRankNAVER()))
                naver = Integer.parseInt(vo.getRankNAVER());
            if(!StringUtils.isEmpty(vo.getRankDAUM()))
                daum = Integer.parseInt(vo.getRankDAUM());
            if(!StringUtils.isEmpty(vo.getRankZUM()))
                zum = Integer.parseInt(vo.getRankZUM());
            if(daum < naver && daum <= zum)
                url = "https://search.daum.net/search?w=tot&q=";
            else if(zum < naver && zum <= daum)
                url = "http://search.zum.com/search.zum?method=uni&query=";
            else
                url = "https://search.naver.com/search.naver?query=";

            url += keyword;
            
            vo.setLink(url);
		}
		
			model.addAttribute("resultList", helloList);

		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmm");

		try {
			Date to = transFormat.parse(String.valueOf(simpleDate));
			model.addAttribute("simpleDate", to);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//keywordDao.findB
		return "web/blog_sum";
	}
	
	@RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
	
}
