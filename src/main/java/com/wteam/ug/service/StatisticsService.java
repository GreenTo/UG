package com.wteam.ug.service;

import com.wteam.ug.entity.Statistics;

import java.util.Date;
import java.util.List;

public interface StatisticsService {
	public boolean add(Date date, long registeredUsers, long activeUsers, long workOrders, long businessVisits);
	
	boolean updateRegisteredUsers(Date date);
	
	boolean updateActiveUsers(Date date);
	
	boolean updateWorkOrders(Date date);
	
	boolean updateBusinessVisits(Date date);
	
	Statistics findByDate(Date date);
	
	List<Statistics> findByDateBetween(Date startDate, Date lastDate);
	
}
