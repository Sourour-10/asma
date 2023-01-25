package tn.esprit.spring.entity;



import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor



public class Enquete implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int enqueteId;
	int valeur1;
	int valeur2;
	int valeur3;
	float moyenne;
	String EnqueteQuestions1;
	String EnqueteQuestions2;
	String EnqueteQuestions3;
	
	}
