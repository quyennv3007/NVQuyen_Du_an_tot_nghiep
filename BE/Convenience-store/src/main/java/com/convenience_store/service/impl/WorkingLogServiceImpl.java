package com.convenience_store.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convenience_store.entity.WorkingLog;
import com.convenience_store.repository.WorkingLogRepo;
import com.convenience_store.service.UserService;
import com.convenience_store.service.WorkingLogService;

@Service
public class WorkingLogServiceImpl implements WorkingLogService{

	@Autowired private WorkingLogRepo repo;
	@Autowired private UserService userService;
	
	@Override
	public void create(Long userId, String action, String entity) {
		WorkingLog log = new WorkingLog(null,new Timestamp(System.currentTimeMillis()),action,entity,userService.findById(userId));
		repo.saveAndFlush(log);
	}

	@Override
	public List<Object[]> getWorkLog() {
		return repo.getWorkLog();
	}

}
