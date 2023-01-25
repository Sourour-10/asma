package tn.esprit.spring.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements  GrantedAuthority 
{ADMIN, EMPLOYEE;

@Override
public String getAuthority() {
	return "ROLE_" + name();
	
}

}