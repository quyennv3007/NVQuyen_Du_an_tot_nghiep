package com.convenience_store.api;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.entity.Authority;
import com.convenience_store.entity.Role;
import com.convenience_store.entity.User;
import com.convenience_store.exception.ResourceNotFoundException;
import com.convenience_store.security.payload.JwtResponse;
import com.convenience_store.security.payload.LoginRequest;
import com.convenience_store.security.payload.MessageResponse;
import com.convenience_store.security.payload.SignupRequest;
import com.convenience_store.security.payload.UpdateUserRequest;
import com.convenience_store.security.userdetailsservice.UserDetailsImpl;
import com.convenience_store.service.AccountLogService;
import com.convenience_store.service.AuthorityService;
import com.convenience_store.service.MailerService;
import com.convenience_store.service.RoleService;
import com.convenience_store.service.UserService;
import com.convenience_store.util.JwtUtils;

@CrossOrigin
@RestController
@RequestMapping("/v1/api/auth")
public class AuthApi {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AuthorityService authService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MailerService mailService;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private ModelMapper mapper;
	@Autowired private AccountLogService accountLogService;

	@GetMapping("/logout")
	public ResponseEntity<?>logout(@RequestHeader("UserId") Long UserId){
		System.out.println(UserId);
		accountLogService.create(UserId,"Đăng xuất","Đăng xuất");
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.ok(new MessageResponse("Error"));
		}
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse("Wrong"));
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		System.out.println("detail:"+userDetails.getUsername()+"--"+userDetails.getPassword());
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		User checkIsDeleted = userService.findById(userDetails.getId());
		if(checkIsDeleted.getIsDeleted()) {
			return ResponseEntity.ok(new MessageResponse("Account is blocked"));
		}
		
		accountLogService.create(userDetails.getId(),"Đăng nhập","Đăng nhập");
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getName(), userDetails.getEmail(), userDetails.getAddress(), userDetails.getGender(),
				userDetails.getMemberShip(), roles));
	}

	@PostMapping("signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userService.existsByPhone(signUpRequest.getPhone())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Phone is already taken!"));
		}

		if (userService.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user
		User user = mapper.map(signUpRequest, User.class);

		Role userRole = roleService.findByName("customer");
		Authority author = new Authority(userRole, user);

		User create = userService.create(user);
		authService.create(author);
		accountLogService.create(create.getId(),"Đăng ký tài khoản","Đăng ký tài khoản");
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PutMapping("update/profile/{id}")
	public ResponseEntity<?> updateProfile(@RequestBody UpdateUserRequest updateUserRequest,
			@PathVariable("id") Long id,@RequestHeader("UserId") Long UserId) {
		// Update user's account
		User user = userService.findById(id);
		String oldPhoto = user.getPhoto();
		if (Objects.isNull(user)) {
			throw new ResourceNotFoundException("Not Found User With ID = " + updateUserRequest.getId() + " !!!");
		}
		System.out.println(updateUserRequest.getPhoto());
		user = mapper.map(updateUserRequest, User.class);
		String photo = updateUserRequest.getPhoto();
		if(!photo.equalsIgnoreCase("empty")) {
			user.setPhoto(updateUserRequest.getPhoto());			
		}else {
			user.setPhoto(oldPhoto);
		}
		try {
			userService.update(user);
			accountLogService.create(UserId,"Cập nhật tài khoản","Cập nhật tài khoản");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Update profile failed!"));
		}

		return ResponseEntity.ok(new MessageResponse("Updated account successfully!"));
	}

	@PutMapping("update/password/{id}")
	public ResponseEntity<?> updatePassword(@RequestBody String password, @PathVariable("id") Long id,@RequestHeader("UserId") Long UserId) {
		// Update pass's account
		try {
			userService.updatePass(password, id);
			accountLogService.create(UserId,"Cập nhật mật khẩu","Cập nhật mật khẩu");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Update password failed!"));
		}

		return ResponseEntity.ok(new MessageResponse("Updated password successfully!"));
	}

	@PostMapping("check/phone")
	public ResponseEntity<?> checkPhone(@RequestBody String phone) {
		// Update pass's account
		try {
			if (userService.existsByPhone(phone)) {
				return ResponseEntity.ok(new MessageResponse("Phone is existed!"));
			} else {
				return ResponseEntity.ok(new MessageResponse("Phone is not existed!"));
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error!!!"));
		}
	}

	@PostMapping("check/email")
	public ResponseEntity<?> checkEmail(@RequestBody String email) {
		// Update pass's account
		try {
			if (userService.existsByEmail(email)) {
				return ResponseEntity.ok(new MessageResponse("Email is existed!"));
			} else {
				return ResponseEntity.ok(new MessageResponse("Email is not existed!"));
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error!!!"));
		}
	}

	@PostMapping("forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody String email) {
		try {
			if (userService.existsByEmail(email)) {
				String randomPass = RandomStringUtils.randomAlphanumeric(6);
				User user = userService.findByEmail(email);
				userService.updatePass(randomPass, user.getId());
				try {
					mailService.send(email, "Send your new password!", "Password của bạn là: " + randomPass);
				} catch (Exception e) {
					System.out.print(e);
				}
				return ResponseEntity.ok(new MessageResponse("Sended new password to email succeed"));
			} else {
				return ResponseEntity.ok(new MessageResponse("Email is not existed!"));
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error!!!"));
		}
	}

	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	@PostMapping("check/password/{id}")
	public ResponseEntity<?> checkPassword(@RequestBody String password, @PathVariable("id") Long id) {
		User user = userService.findById(id);
		System.out.println("run");
		if (bcrypt.matches(password, user.getPassword())) {
			return ResponseEntity.ok(new MessageResponse("Matched"));
		}
		return ResponseEntity.ok(new MessageResponse("UnMatched"));
	}

}
