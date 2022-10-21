package com.convenience_store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.convenience_store.dto.AuthorityDTO;
import com.convenience_store.dto.UserDTO;
import com.convenience_store.entity.MemberShip;
import com.convenience_store.entity.User;
import com.convenience_store.repository.UserRepo;
import com.convenience_store.service.AuthorityService;
import com.convenience_store.service.MemberShipService;
import com.convenience_store.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	@Autowired
	private UserRepo repo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private MemberShipService memberShipService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Override
	public List<UserDTO> findAll() {
		List<User> users = repo.findByIsDeleted(Boolean.FALSE);
		List<UserDTO> listUserDTO = new ArrayList<>();
		for(User item : users) {
			UserDTO userDTO = mapper.map(item, UserDTO.class);
			List<AuthorityDTO> authorityDTO = authorityService.findByUser(item);
			userDTO.setAuthorityDTO(authorityDTO);
			listUserDTO.add(userDTO);
		}
		return listUserDTO;
	}

	@Transactional
	@Override
	public User create(User user) {
		MemberShip memberShip = memberShipService.findByType("Silver");
		user.setIsDeleted(Boolean.FALSE);
		user.setMemberShip(memberShip);
		user.setPassword(bcrypt.encode(user.getPassword()));
		return repo.saveAndFlush(user);
	}
	
	@Transactional
	@Override
	public User createAnonymous(User user) {
		MemberShip memberShip = memberShipService.findByType("Silver");
		user.setIsDeleted(Boolean.TRUE);
		user.setMemberShip(memberShip);
		user.setPassword(bcrypt.encode(user.getPassword()));
		return repo.saveAndFlush(user);
	}
	
	@Transactional
	@Override
	public void update(User user) throws NullPointerException {
		if (ObjectUtils.isEmpty(user)) {
			throw new NullPointerException("User is not null");
		}
		try {
			repo.updateProfile(user.getName(), user.getEmail(), 
					user.getPhone(), user.getAddress(), 
						user.getGender() , user.getPhoto(), user.getId() );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@Override
	public void updatePass(String password, Long id) {
		try {
			password = bcrypt.encode(password);
			repo.updatePass(password, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@Override
	public void delete(Long id) {
		repo.deleteLogical(id);
	}

	@Override
	public Boolean existsByPhone(String phone) {
		return repo.existsByPhone(phone);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return repo.existsByEmail(email);
	}

	@Override
	public User findById(Long Id) {
		Optional<User> user = repo.findById(Id);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public User create_Admin(User user) {
		return repo.save(user);
	}

	@Override
	public void delete(User user) {
		repo.save(user);
	}

	@Override
	public List<UserDTO> findAllDeleted() {
		List<User> users = repo.findByIsDeleted(Boolean.TRUE);
		List<UserDTO> listUserDTO = new ArrayList<>();
		for(User item : users) {
			UserDTO userDTO = mapper.map(item, UserDTO.class);
			List<AuthorityDTO> authorityDTO = authorityService.findByUser(item);
			userDTO.setAuthorityDTO(authorityDTO);
			listUserDTO.add(userDTO);
		}
		return listUserDTO;
	}

	@Override
	public void recover(User user) {
		repo.saveAndFlush(user);
		
	}
}
