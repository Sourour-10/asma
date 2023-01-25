package tn.esprit.spring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import tn.esprit.spring.repository.UserRepository;

@Component("userSecurity")
public class UserSecurity {
@Autowired
UserRepository userRepository;
public boolean hasUserId(Authentication authentication, Integer userId) {
	
	int userID=userRepository.findByUserName(authentication.getName()).getUserId();

        if(userID==userId)
        	return true;
        
        return false;
   
}
}
