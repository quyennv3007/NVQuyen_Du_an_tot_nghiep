package com.convenience_store.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.dto.UserDTO;
import com.convenience_store.entity.User;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/v1/api")
public class UserApi {

	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public ResponseEntity<?> getAllUsers() {
		List<UserDTO> userDTO = userService.findAll();
		if (userDTO.isEmpty()) {
			throw new ResourceNotFoundException("Not Found Users!!");
		}
		return ResponseEntity.ok(userDTO);
	}

	@PostMapping("/user")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		try {
			userService.create(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@PutMapping("/user")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		try {
			userService.update(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		try {
			userService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
}
