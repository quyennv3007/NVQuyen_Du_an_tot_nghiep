package com.convenience_store.api.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.service.AccountLogService;
import com.convenience_store.service.WorkingLogService;

@CrossOrigin
@RestController
@RequestMapping("/v1/api/admin")
public class LogApi_Admin {
	
	@Autowired private AccountLogService accLogService;
	@Autowired private WorkingLogService workLogService;
	
	@GetMapping("log")
	public Map<String,List<Object[]>> getLog() {
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("accountLog", accLogService.getAccLog());
		map.put("workingLog", workLogService.getWorkLog());
		return map;
	}
}
