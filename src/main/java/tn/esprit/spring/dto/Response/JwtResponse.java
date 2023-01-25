package tn.esprit.spring.dto.Response;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
public class JwtResponse {
	private String token;
	  private String type = "Bearer";
		private int Id;
	  private String username;
		private String email;
		


		private String firstname;
		private String password;
		private String lastname;
		private Long phoneNumber;
		private Date birthdate;
		private Long   idPhoto; 
	 
		//private List<String> roles;
		
private Collection<? extends GrantedAuthority> authorities;
	  
	  
	  public JwtResponse(String accessToken , int Id,String username, String email, String password,String firstname,
				String lastname,Long phoneNumber,Date birthdate,Collection<? extends GrantedAuthority> authorities) {
		    this.token = accessToken;
		    //this.refreshToken=refreshToken;
		  this.setId(Id);
		    this.username = username;
		    this.authorities=authorities;
		  this.email=email;
		  this.password=password;
		  this.firstname=firstname;
		  this.lastname=lastname;
		  this.phoneNumber=phoneNumber;
		  this.birthdate=birthdate;
		  //this.idPhoto=idPhoto;
		 // this.setRoles(roles);
		  
		    
		    
		  }
	  public JwtResponse(String accessToken , int Id,String username, String email, String password,String firstname,
				String lastname,Long phoneNumber,Date birthdate ,long idPhoto,Collection<? extends GrantedAuthority> authorities) {
		    this.token = accessToken;
		    //this.refreshToken=refreshToken;
		  this.setId(Id);
		    this.username = username;
		    this.authorities=authorities;
		  this.email=email;
		  this.password=password;
		  this.firstname=firstname;
		  this.lastname=lastname;
		  this.phoneNumber=phoneNumber;
		  this.birthdate=birthdate;
		  this.idPhoto=idPhoto;
		 // this.setRoles(roles);
		  
	  }
	  
	  
	

	public String getAccessToken() {
		    return token;
		  }
	  

		  public void setAccessToken(String accessToken) {
		    this.token = accessToken;
		  }

		  public String getTokenType() {
		    return type;
		  }

		  public void setTokenType(String tokenType) {
		    this.type = tokenType;
		  }








		public String getUsername() {
			return username;
		}





		public void setUsername(String username) {
			this.username = username;
		}





		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}





		public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
			this.authorities = authorities;
		}

		public String getToken() {
			return token;
		}





		public void setToken(String token) {
			this.token = token;
		}





		public String getType() {
			return type;
		}





		public void setType(String type) {
			this.type = type;
		}





		public String getEmail() {
			return email;
		}





		public void setEmail(String email) {
			this.email = email;
		}





		public String getFirstname() {
			return firstname;
		}





		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}





		public String getPassword() {
			return password;
		}





		public void setPassword(String password) {
			this.password = password;
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





		public int getId() {
			return Id;
		}





		public void setId(int id) {
			this.Id = id;
		}





		public Long getIdPhoto() {
			return idPhoto;
		}





		public void setIdPhoto(Long idPhoto) {
			this.idPhoto = idPhoto;
		}





		



}
