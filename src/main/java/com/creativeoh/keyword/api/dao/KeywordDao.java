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
	 * 지금 사용 : 해당 시점 데이터 가져오기
	 * @Query("SELECT k.keyword, sum(k.point) as point FROM Keyword k where k.point > 0 and k.typeCode = 1 and k.simpleDate = :simpleDate Group by k.keyword order by point desc")
	 */
	@Query(value=" select "
			+ "case when b.id is not null then b.id else case when c.id is not null then c.id else case when d.id is not null then d.id else -1 end end end as id "
			+ ", '' as from_site, '-1' as rank, now() as reg_date, :simpleDate as simple_date, 2 as type_code,"
			+ "a.keyword, a.point , b.rank as rankNAVER, c.rank as rankDAUM, d.rank as rankZUM"
+ " from(SELECT k.keyword, sum(k.point) as point FROM Keyword k where k.point > 0 and k.type_code = 1 and k.simple_date = :simpleDate Group by k.keyword ) a"
+ " left join Keyword b on a.keyword = b.keyword and b.from_Site = 'NAVER' and b.type_code = 1 and b.simple_date = :simpleDate"
+ " left join keyword c on a.keyword = c.keyword and c.from_site = 'DAUM' and c.type_code = 1 and c.simple_date = :simpleDate"
+ " left join keyword d on a.keyword = d.keyword and d.from_site = 'ZUM' and d.type_code = 1 and d.simple_date = :simpleDate"
+ " order by a.point desc", nativeQuery=true)
	List<Keyword> findBySimpleDateSum(@Param("simpleDate") long simpleDate);
	
	/*
	 *(typeCode는 2, 일 경우, 해당 시점까지의 누적 가져오기), typeCode = 1 일 경우, 해당 시점 사이트별 모든 내용 가져오기(group sum 하지 않음)
	 * 지금 사용 : 해당 시점에 만들어진 누적 데이터 가져오기 type 2 and simpleDate 
	 */
	
	List<Keyword> findByTypeCodeAndSimpleDate(int typeCode, long simpleDate);
	
	
	//가장 최신것 가져오기
	Keyword findTopByTypeCodeOrderBySimpleDateDesc(int sum);

}
