package com.wteam.ug.repository;

import com.wteam.ug.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface StatisticsRepository extends JpaRepository<Statistics,Long>{

	Statistics findByDate(Date date);
	
	List<Statistics> findByDateBetween(Date startDate, Date lastDate);
}
