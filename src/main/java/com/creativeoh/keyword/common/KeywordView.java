package com.creativeoh.keyword.common;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class KeywordView implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2444584774728974042L;
	@Id	@Getter @Setter long id;
	@Getter @Setter private String keyword;
	@Getter @Setter private double point;
	@Getter @Setter private long simpleDate;
	
}
