package tn.esprit.spring.entity;

import java.io.Serializable;
import java.util.Date;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@Lazy
@DynamicUpdate

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Les attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int userId;
	

	String userName;
	String password;
	
	String mail;
	long phoneNumber;
	String firstName;
	String lastName;
	String cin;
	@Temporal(TemporalType.DATE)
	Date birthdate;
	// Anas
	int ratingNumber;
	float rating;
	int points;
	int numberOfVote;
	boolean hasVoted;
	// fin anas
	Boolean active;
	@Enumerated(EnumType.STRING)
	Role role;
	

	public User(int userId, String userName, String password) {
		super();

		this.userName = userName;
		this.password = password;
		this.userId = userId;

	}
	public  User(int userId,String mail,String firstName,String lastname, String password){
		super();
		
		this.firstName = firstName;
		this.lastName = lastname;

		this.mail = mail;
	
		this.password = password;

	}
	public User(String firstName, String lastName, String email, String userName, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;

		this.mail = email;
		this.userName = userName;
		this.password = password;

	}
	public User(String firstName, String lastName, String email, String userName, 		Date birthdate, String cin, long phoneNumber,String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;

		this.mail = email;
		this.userName = userName;
		this.password = password;
		this.birthdate=birthdate;
		this.cin=cin;
		this.phoneNumber=phoneNumber;
		

	}

	public User(String username, String email, String password, String firstname) {
		this.userName = username;
		this.mail = email;
		this.password = password;
		this.firstName = firstname;
	}




	// Les associations
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private Photo photo;
	@JsonIgnore

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Notification> notifications;
	@JsonIgnore

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Article> articles;
	@JsonIgnore

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Event> events;
	@JsonIgnore

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Activity> activities;
	@JsonIgnore

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
	private Set<Activity> activitiesLiked;
	@JsonIgnore

	@OneToMany(cascade = CascadeType.ALL)
	private Set<FeedBack> feedBacks;
	@JsonIgnore

	@ManyToMany(cascade = CascadeType.ALL) // , mappedBy = "users")
	private Set<Poll> polls;
	@JsonIgnore

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "users")
	private Set<Training> trainings;
	@JsonIgnore

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Badge> badges;
	@JsonIgnore

	@ManyToOne
	Department department;
	@JsonIgnore

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Publicity> publicities;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Collaboration> collaborations;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Reclamation> reclamations;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "UserLike")
	private List<React> likes;
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Participate> participates;
	@JsonIgnore

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Post> posts;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Comment> comments;
}