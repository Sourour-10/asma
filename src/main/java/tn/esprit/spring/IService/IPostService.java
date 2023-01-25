package tn.esprit.spring.IService;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.entity.Post;



public interface IPostService {
	//CRUD
	void createPost(Post Post,int userId) ;
	void deletePost(int  idPost) ;
	void updatePost(Post Post);
	public List<Post> getAllPosts();
	public String createPostForbidden(Post Post,int userId);
	public void setForbiddenWords(String words);
	public String getForbiddenWords() ;
	public void SharePost(int PostId, int UserId,Post Share);
	public List<Post> GetSharedPosts(int PostId);
	public Post getPostById(int id);
	public int getNcomments(int PostId);
	public int getNlikes(int PostId);
	public int getNshares(int PostId);
	public List<Post> GetMyPosts(int UserId);
	public ResponseEntity<byte[]> getPhotoByPost(int postId);
	

}
