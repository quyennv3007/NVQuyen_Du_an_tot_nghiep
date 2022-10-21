package com.convenience_store.api.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.dto.UserDTO;
import com.convenience_store.entity.Authority;
import com.convenience_store.entity.MemberShip;
import com.convenience_store.entity.User;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.security.payload.MessageResponse;
import com.convenience_store.service.AuthorityService;
import com.convenience_store.service.MemberShipService;
import com.convenience_store.service.UserService;
import com.convenience_store.service.WorkingLogService;
import com.fasterxml.jackson.databind.JsonNode;

@CrossOrigin
@RestController
@RequestMapping("/v1/api/admin")
public class UserApi_Admin {
	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();;
	@Autowired private UserService userService;
	@Autowired private MemberShipService memberShipService;
	@Autowired private AuthorityService authorityService;
	@Autowired private WorkingLogService workingLogService;
	
	@GetMapping("/user")
	public ResponseEntity<?> getAllUsers() {
		List<UserDTO> userDTO = userService.findAll();
		if (userDTO.isEmpty()) {
			throw new ResourceNotFoundException("Not Found Users!!");
		}
		return ResponseEntity.ok(userDTO);
	}
	
	@GetMapping("/userDeleted")
	public ResponseEntity<?> getAllDeletedUsers() {
		List<UserDTO> userDTO = new ArrayList<UserDTO>();
		try {
			userDTO = userService.findAllDeleted();
		} catch (Exception e) {
			return ResponseEntity.ok(new MessageResponse("null"));
		}
		if (userDTO.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("null"));
		}
		return ResponseEntity.ok(userDTO);
	}
	
	@PostMapping("/account")
	public ResponseEntity<?>addAccount(@RequestBody User user,@RequestHeader("UserId") Long UserId){
		MemberShip membership = memberShipService.findById(user.getMemberShip().getId());
		user.setMemberShip(membership);
		user.setPassword(bcrypt.encode(user.getPassword()));
		User temp = userService.create_Admin(user);

		List<Authority> authorities = user.getAuthorities();
		for (Authority item : authorities) {
			item.setUser(temp);
		}
		authorityService.create_Admin(authorities);
		workingLogService.create(UserId,"Tạo tài khoản có Id: "+temp.getId(),"Table User");
		return null;
	}
	
	@PutMapping("/account/{id}")
	public ResponseEntity<?>updateAccount(@RequestBody User user,@PathVariable("id")Long userId,@RequestHeader("UserId") Long UserId){
		try {
			System.out.println("run");
			MemberShip membership = memberShipService.findById(user.getMemberShip().getId());
			user.setMemberShip(membership);
			//Remove all old authority
			authorityService.removeByUserId(userId);
			
			User temp = userService.findById(userId);
			user.setPassword(temp.getPassword());
			userService.create_Admin(user);
			
			List<Authority> authorities = user.getAuthorities();
			for (Authority item : authorities) {
				item.setUser(temp);
			}
			//add new authority
			authorityService.create_Admin(authorities);
			workingLogService.create(UserId,"Cập nhật tài khoản có Id: "+userId,"Table User");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
