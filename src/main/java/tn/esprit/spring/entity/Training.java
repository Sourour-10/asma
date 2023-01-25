package tn.esprit.spring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

public class Training implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int trainingId;

	String title;
	String description;
	
	@Temporal(TemporalType.DATE)

	Date StartDate;

	@Temporal(TemporalType.DATE)

	Date EndDate;

	int hoursNumbers;
	int MaxParticipant;
	int costs;
	Boolean certified;
	// Anas
	float rating;
	int ratingNumber;
	int points;

	// Les association
	// Les associations
	@ManyToOne
	@JsonIgnore
	private Coach coach;
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<User> users;

}
