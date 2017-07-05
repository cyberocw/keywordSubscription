package com.creativeoh.keyword.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.creativeoh.keyword.Keyword;

public interface KeywordDao extends JpaRepository<Keyword, Long> {
	
	@Query("SELECT k.keyword, sum(k.point) as point, k.fromSite FROM Keyword k where k.point > 0 and k.typeCode = :typeCode and k.simpleDate >= :simpleDate Group by k.keyword, k.fromSite order by point desc")
	List<Keyword> findBySimpleDateGroupGreaterThanEqual(@Param("typeCode") int typeCode, @Param("simpleDate") long simpleDate);
	
	List<Keyword> findByTypeCodeAndSimpleDate(int typeCode, long today);
	
	Keyword findTopByTypeCodeOrderBySimpleDateDesc(int sum);

}
