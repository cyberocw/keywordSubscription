package com.creativeoh.keyword.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.creativeoh.keyword.Keyword;

public interface KeywordDao extends JpaRepository<Keyword, Long> {
	
	/*
	 * simpleDate 부터~~~ (누적 sum) (자동에서만 쓰임)
	 */
	@Query("SELECT k.keyword, sum(k.point) as point FROM Keyword k where k.point > 0 and k.typeCode = :typeCode and k.simpleDate >= :simpleDate Group by k.keyword order by point desc")
	List<Keyword> findBySimpleDateGroupGreaterThanEqual(@Param("typeCode") int typeCode, @Param("simpleDate") long simpleDate);
	
	/*
	 * 해당 시점 (type 1)simpleDate 통계 (시간대별 실시간) -> 결국 group, sum 해줌 - mode SUM
	 */
	@Query("SELECT k.keyword, sum(k.point) as point FROM Keyword k where k.point > 0 and k.typeCode = 1 and k.simpleDate = :simpleDate Group by k.keyword order by point desc")
	List<Keyword> findBySimpleDateSum(@Param("simpleDate") long simpleDate);
	
	/*
	 *(typeCode는 2, 일 경우, 해당 시점까지의 누적 가져오기), typeCode = 1 일 경우, 해당 시점 사이트별 모든 내용 가져오기(group sum 하지 않음) 
	 */
	
	List<Keyword> findByTypeCodeAndSimpleDate(int typeCode, long simpleDate);
	
	
	//가장 최신것 가져오기
	Keyword findTopByTypeCodeOrderBySimpleDateDesc(int sum);

}
