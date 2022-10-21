package com.convenience_store.security.userdetailsservice;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.convenience_store.dto.MemberShipDto;
import com.convenience_store.entity.MemberShip;
import com.convenience_store.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	@JsonIgnore
	private String password;
	private String name;
	private String email;
	private String phone;
	private String address;
	private Boolean gender;
	private MemberShipDto memberShip; 
	private Collection<? extends GrantedAuthority> authorities;
	
	

	public UserDetailsImpl(Long id, String password, String name, String email, String phone, String address,
			Boolean gender, MemberShipDto memberShip, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.memberShip = memberShip;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getAuthorities().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
				.collect(Collectors.toList());
		return new UserDetailsImpl(
				user.getId(), 
				user.getPassword(), 
				user.getName(),
				user.getEmail(),
				user.getPhone(),
				user.getAddress(),
				user.getGender(),
				user.getMemberShip(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public Boolean getGender() {
		return gender;
	}
	
	public MemberShipDto getMemberShip() {
		return memberShip;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return phone;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(phone, user.phone);
	}

}
