package com.convenience_store.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.convenience_store.entity.AccountLog;
import com.convenience_store.repository.AccountLogRepo;
import com.convenience_store.service.AccountLogService;
import com.convenience_store.service.UserService;

@Service
public class AccountLogServiceImpl implements AccountLogService{

	@Autowired private AccountLogRepo repo;
	@Autowired private UserService userService;
	
	@Override
	public void create(Long userId, String action, String description) {
		AccountLog acc = new AccountLog(null,new Timestamp(System.currentTimeMillis()),action,description,userService.findById(userId));
		repo.saveAndFlush(acc);
	}
	
	@Override
	public List<Object[]> getAccLog(){
		return repo.getAccLog();
	}

}
