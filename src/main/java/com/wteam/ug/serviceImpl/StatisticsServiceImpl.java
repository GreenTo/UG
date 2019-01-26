package com.wteam.ug.serviceImpl;

import com.wteam.ug.entity.Statistics;
import com.wteam.ug.repository.StatisticsRepository;
import com.wteam.ug.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class StatisticsServiceImpl implements StatisticsService{
	
	@Autowired
	StatisticsRepository statisticsRepository;

	@Override
	public boolean add(Date date,long registeredUsers,long activeUsers, long workOrders,long businessVisits) {
		boolean b;
		try {
			Statistics statistics = new Statistics();
			statistics.setDate(date);
			statistics.setRegisteredUsers(registeredUsers);
			statistics.setActiveUsers(activeUsers);
			statistics.setWorkOrders(workOrders);
			statistics.setBusinessVisits(businessVisits);
			statisticsRepository.save(statistics);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public boolean updateRegisteredUsers(Date date) {
		boolean b;
		try {
			Statistics statistics = statisticsRepository.findByDate(date);
			if(statistics == null) {
				add(date, 1, 0, 0 , 0);
				return true;
			}
			long registeredUsers = statistics.getRegisteredUsers();
			statistics.setRegisteredUsers(registeredUsers+1);
			statisticsRepository.save(statistics);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public boolean updateActiveUsers(Date date) {
		boolean b;
		try {
			Statistics statistics = statisticsRepository.findByDate(date);
			if(statistics == null) {
				add(date, 0, 1, 0 , 0);
				return true;
			}
			long activeUsers = statistics.getActiveUsers();
			statistics.setActiveUsers(activeUsers+1);
			statisticsRepository.save(statistics);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public boolean updateWorkOrders(Date date) {
		boolean b;
		try {
			Statistics statistics = statisticsRepository.findByDate(date);
			if(statistics == null) {
				add(date, 0, 0, 1 , 0);
				return true;
			}
			long workOrders = statistics.getWorkOrders();
			statistics.setWorkOrders(workOrders+1);
			statisticsRepository.save(statistics);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public boolean updateBusinessVisits(Date date) {
		boolean b;
		try {
			Statistics statistics = statisticsRepository.findByDate(date);
			if(statistics == null) {
				add(date, 0, 0, 0 , 1);
				return true;
			}
			long businessVisits = statistics.getBusinessVisits();
			statistics.setBusinessVisits(businessVisits+1);
			statisticsRepository.save(statistics);
			b=true;
		}catch (RuntimeException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	@Override
	public Statistics findByDate(Date date) {
		Statistics statistics = statisticsRepository.findByDate(date);
		return statistics;
	}

	@Override
	public List<Statistics> findByDateBetween(Date startDate, Date lastDate) {
		List<Statistics> statistics = statisticsRepository.findByDateBetween(startDate,lastDate);
		return statistics;
	}





}
