package com.convenience_store.security.userdetailsservice;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.convenience_store.entity.User;
import com.convenience_store.repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepo repo;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
		User user = repo.findByPhone(phone).orElseThrow(() -> new UsernameNotFoundException("User Not Found with phone: " + phone));
		return UserDetailsImpl.build(user);
	}

}
