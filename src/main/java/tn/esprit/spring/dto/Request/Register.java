

package tn.esprit.spring.dto.Request;
import java.util.Date;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Register {

	  

	private String username;

	 
	  private String email;
	  
	 private String firstname;
	 private String lastname;
	 Long idPhoto;
	
	
	String cin;
		@Temporal(TemporalType.DATE)
		Date birthdate;
	  private Set<String> role;
	  long phoneNumber;

	
	  private String password;

	  public String getUsername() {
	    return username;
	  }

	  public void setUsername(String username) {
	    this.username = username;
	  }

	  public String getEmail() {
	    return email;
	  }

	  public void setEmail(String email) {
	    this.email = email;
	  }

	  public String getPassword() {
	    return password;
	  }

	  public void setPassword(String password) {
	    this.password = password;
	  }

	  public Set<String> getRole() {
	    return this.role;
	  }

	  public void setRole(Set<String> role) {
	    this.role = role;
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

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	 public Long getIdPhoto() {
			return idPhoto;
		}

		public void setIdPhoto(Long idPhoto) {
			this.idPhoto = idPhoto;
		}
	@Override
	public String toString() {
		return "Register [username=" + username + ", email=" + email + ", firstname=" + firstname + ", lastname="
				+ lastname + ", role=" + role + ", password=" + password + ", phoneNumeber="
						+ phoneNumber + ", cin=" + cin + ", birthdate=" + birthdate + ", idPhoto=" + idPhoto + "]";
	}

	
}
