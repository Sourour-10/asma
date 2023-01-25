package tn.esprit.spring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.OneToMany;

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
public class React implements Serializable {
	private static final long serialVersionUID = 1L;
	// Les attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int reactId;
	@Enumerated(EnumType.STRING)
	Reaction reaction;

	@ManyToOne
	Post PostLike;
	
	@ManyToOne
	Comment CommentLike;
	
	@ManyToOne
	User UserLike;
	
	@JsonBackReference
	@ManyToOne
	Response ResponseLike;

}
