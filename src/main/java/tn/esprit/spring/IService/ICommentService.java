package tn.esprit.spring.IService;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

import tn.esprit.spring.entity.Comment;
import tn.esprit.spring.entity.Post;
import tn.esprit.spring.entity.utils.PagingResponse;


public interface ICommentService {
	//CRUD
	void createComment(Comment comment,int userId) ;
	void deleteComment(int  idcomment) ;
	void updateComment(Comment comment);
	public List<Comment> getAllComments();
	public List<Comment> getCommentByPost(int postId);
	public void affectNewCommentToPost(int PostID,int userId, Comment comment);
  public PagingResponse get(Specification<Comment> spec, HttpHeaders headers, Sort sort);
  public void ReplyToComment(int CommentId,String reply);
  public Comment getCommentById(int CommentId);
  public List<Comment> getRepliesByComment(int CommentId);
	

}
