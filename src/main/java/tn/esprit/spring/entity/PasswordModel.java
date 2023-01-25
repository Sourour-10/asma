package tn.esprit.spring.entity;

import lombok.Data;

@Data
public class PasswordModel {
	String mail;
	String token;
	String oldPassword;
	String newPassword;
}
