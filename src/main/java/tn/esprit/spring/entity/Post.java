package tn.esprit.spring.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class Post implements Serializable {
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	private String text;
	@Temporal(TemporalType.DATE)
	private Date date;
	private int Nlikes;
	@JsonIgnore
	@ManyToOne
	private User user;
	@JsonIgnore
	@ManyToOne
    private Post parent;
	@JsonIgnore
    @OneToMany(mappedBy = "parent")
    private List<Post> children = new ArrayList<Post>();
	@JsonIgnore 
	@OneToMany(cascade = CascadeType.ALL,mappedBy="PostCommented")
	private List<Comment> comments;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,mappedBy="PostLike")
	private List<React> likes; 
	@OneToOne(cascade = CascadeType.ALL)
	private Photo photo;
	

	
	
 
	
}
