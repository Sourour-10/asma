package tn.esprit.spring.service;

import java.util.ArrayList;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entity.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
			 
			if (userName.trim().isEmpty()) {
				throw new UsernameNotFoundException("username is empty");
			}
	 
			User user = userService.findUserByUserName(userName);
	 
			if (user == null) {
				throw new UsernameNotFoundException("User " + userName + " not found");
			}
		
			
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getGrantedAuthorities(user));
		}
	 
		private List<GrantedAuthority> getGrantedAuthorities(User user) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			
			authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
			return authorities;
		}

}
