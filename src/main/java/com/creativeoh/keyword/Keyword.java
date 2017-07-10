package com.creativeoh.keyword;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Keyword implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5282881657187484018L;
	@Id
	@GeneratedValue
	@Getter @Setter private long id = -1;
	@Getter @Setter private String keyword ="";
	@Getter @Setter private int rank = -1;
	@Getter @Setter private String rankNAVER = "";
	@Getter @Setter private String rankDAUM = "";
	@Getter @Setter private String rankZUM = "";
	@Getter @Setter private String fromSite = "";
	@Getter @Setter private float point = 0;
	@Getter @Setter private Date regDate;
	@Getter @Setter private long simpleDate;
	@Getter @Setter private int typeCode;
	@Override
	public String toString() {
		return "Keyword [id=" + id + ", keyword=" + keyword + ", rank=" + rank + ", rankNAVER=" + rankNAVER
				+ ", rankDAUM=" + rankDAUM + ", rankZUM=" + rankZUM + ", fromSite=" + fromSite + ", point=" + point
				+ ", regDate=" + regDate + ", simpleDate=" + simpleDate + ", typeCode=" + typeCode + "]";
	}
	
	
	
}
