package tn.esprit.spring.Security.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;


import tn.esprit.spring.entity.User;


public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private int id;

	private String username;

	private String email;
	
	private String firstname;
	
	private String lastname;
	private Long phoneNumber;
	private Date birthdate;
	//List<String> roles;
	
	@JsonIgnore
	private String password;
	private Long   idPhoto; 

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(int id, String username, String email, String password,String firstname,
			String lastname, Long phoneNumber,Date birthdate, Long  idPhoto,Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstname=firstname;
		this.lastname=lastname;
		this.phoneNumber=phoneNumber;
		this.birthdate=birthdate;
		this.idPhoto=idPhoto;
		this.authorities = authorities;
		
	}

	public UserDetailsImpl(int id, String username, String email, String password,String firstname,
			String lastname, Long phoneNumber,Date birthdate,Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstname=firstname;
		this.lastname=lastname;
		this.phoneNumber=phoneNumber;
		this.birthdate=birthdate;
		//this.idPhoto=idPhoto;
		this.authorities = authorities;
		
	}
	
	
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities =new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
		System.out.println("inside details impl " + authorities.get(0).getAuthority());
		return new UserDetailsImpl(
				user.getUserId(), 
				user.getUserName(), 
				user.getMail(),
				user.getPassword(),
				user.getFirstName(),
				user.getLastName(),
				user.getPhoneNumber(),
				user.getBirthdate(),
				user.getPhoto().getId(),
				authorities);
	}
	
	
	
	


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}
	
	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}
	
	public String setPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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
		return Objects.equals(id, user.id);
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public Long getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public Date getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}


	public Long getIdPhoto() {
		return idPhoto;
	}


	public void setIdPhoto(Long idPhoto) {
		this.idPhoto = idPhoto;
	}


	
}
