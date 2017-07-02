package com.creativeoh.keyword.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;

import com.creativeoh.keyword.Keyword;
import com.creativeoh.keyword.common.util.Utils;

public abstract class KeywordBaseAbs {
	public abstract String getUrl();
	public abstract String getSelectorParent();
	public abstract String getSelectorRank();
	public abstract String getSelectorKeyword();
	public abstract String getSiteName();
	public abstract float getBasePoint();
		
	public int getTimePoint(){
		int point = 0;
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		//2~ 6까지는 pint 낮음
		if(hour >= 7 && hour <= 10){
			point+=2.6;
		}
		else if(hour >= 11 && hour <= 17){
			point+=2.4;
		}
		else if(hour >= 18 && hour <= 2){
			point+=2.7;
		}
		return point;
	}
	
	public int getRankPoint(int rank){
		return 11 - rank;
	}
	
	public float getCalcuratePoint(int rank){
		float point = 0;
		point = getBasePoint() + getTimePoint() + getRankPoint(rank);
		
		return point;
	}
	
	public ArrayList<Keyword> collect(ArrayList<Keyword> arrList, HashMap<String, Keyword> keywordMap){
		String url = getUrl();
		String selectorParent = getSelectorParent();
		String selectorRank = getSelectorRank();
		String selectorKeyword = getSelectorKeyword();
		String siteName = getSiteName();
		
		//ArrayList<Keyword> arrKeyword = new ArrayList<Keyword>();
		try {
			Document doc;
			
			if(url.indexOf("http") > -1){
				 doc = Jsoup.connect(url) .userAgent("Mozilla")
						 .cookie("auth", "token") .timeout(3000) .get();
			}
			else{
				File f = new ClassPathResource(url).getFile();
	
				System.out.println("exist=" + f.exists());
				doc = Jsoup.parse(f, "UTF-8");
			}
			Elements lis = doc.select(selectorParent);
			
			int idx = 0;
			Date date = new Date();
			float addPoint = 1.8f;
			
			for(Element e : lis){
				++idx;
				
				if(idx == 11){
					break;
				}
				
				Keyword vo = new Keyword();
				
				if(selectorRank.equals(""))
					vo.setRank(idx);
				else
					vo.setRank(Integer.parseInt(e.select(selectorRank).text()));
				
				vo.setKeyword(e.select(selectorKeyword).text().trim());
				
				vo.setFromSite(siteName);
				vo.setPoint(getCalcuratePoint(vo.getRank()));
				vo.setRegDate(date);
				vo.setSimpleDate(Utils.makeSimpleDate(date));
				vo.setTypeCode(Codes.KEYWORD_TYPE.BASE);
				
				if(keywordMap.containsKey(vo.getKeyword())){
					Keyword keyword = keywordMap.get(vo.getKeyword());
					if(keyword.getPoint() < vo.getPoint()){
						vo.setPoint(vo.getPoint() + addPoint);
						keywordMap.put(vo.getKeyword(), vo);
						keyword.setPoint(0);
						
						/*
						for(int i = 0 ; i < arrList.size(); i++){
							if(arrList.get(i).getKeyword().equals(vo.getKeyword())){
								arrList.remove(i);
								arrList.add(vo);
								break;
							}
						}*/
					}
					//기존것보다 점수 작으면, 기존것에 점수 +해줌
					else{
						vo.setPoint(0);
						keyword.setPoint(keyword.getPoint() + addPoint);
					}
				}
				//기존에 등록된게 없으면 맵에 바로 추가
				else{
					keywordMap.put(vo.getKeyword(), vo);
				}
				arrList.add(vo);
				
				//System.out.println("rank = " + rank + " val - " + val);
			}
			
			/*
			 * Elements titles = doc.select(".title");
			 * 
			 * //print all titles in main page for(Element e: titles){
			 * System.out.println("text: " +e.text());
			 * System.out.println("html: "+ e.html()); }
			 * 
			 * //print all available links on page Elements links =
			 * doc.select("a[href]"); for(Element l: links){
			 * System.out.println("link: " +l.attr("abs:href")); }
			 */ } catch (IOException e) {
			e.printStackTrace();
		}
		return arrList;
	}
}
