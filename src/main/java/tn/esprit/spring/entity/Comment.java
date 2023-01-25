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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

public class Comment implements Serializable {
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@JsonIgnore
	@JsonProperty(value = "PostCommented")
	public Post getPostCommented() {
		return PostCommented;
	}
	public void setPostCommented(Post postCommented) {
		PostCommented = postCommented;
	}
	public int getNlikes() {
		return Nlikes;
	}
	public void setNlikes(int nlikes) {
		Nlikes = nlikes;
	}
	@JsonIgnore
	@JsonProperty(value = "likes")
	public List<React> getLikes() {
		return likes;
	}
	public void setLikes(List<React> likes) {
		this.likes = likes;
	}
	@JsonIgnore
	@JsonProperty(value = "user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
     int commentId ;
     String text ;
	@Temporal(TemporalType.DATE)
     Date date ;
	//Set<Integer> idcomments ;
	//Les associations
	@ManyToOne
	Post PostCommented;
	private int Nlikes;
	@OneToMany(cascade = CascadeType.ALL,mappedBy="CommentLike")
	private List<React> likes;
	@ManyToOne
	private User user;
	
	@ManyToOne
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<Comment>();

	public Comment getParent() {
		return parent;
	}
	public void setParent(Comment parent) {
		this.parent = parent;
	}
	@JsonIgnore
	@JsonProperty(value = "children")
	public List<Comment> getChildren() {
		return children;
	}
	public void setChildren(List<Comment> children) {
		this.children = children;
	}
	
	
	

     

}
