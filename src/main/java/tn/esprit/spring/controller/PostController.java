package tn.esprit.spring.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.spring.IService.IPostService;
import tn.esprit.spring.entity.Post;
import tn.esprit.spring.entity.User;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/Post")
public class PostController {
	@Autowired
	IPostService PostService;

	// http://localhost:8089/Post/create

	@PutMapping("create/{userId}")
	@ResponseBody
	public void createPost(@RequestBody Post Post,@PathVariable("userId") int userId) {
		PostService.createPost(Post,userId);
	}
	// http://localhost:8089/Post/SharePost

	@PutMapping("SharePost/{PostId}/{userId}")
	@ResponseBody
	public void SharePost(@RequestBody Post post,@PathVariable("PostId") int PostId,@PathVariable("userId") int userId) {
		PostService.SharePost(PostId,userId,post);
	}
	
	// http://localhost:8089/Post/GetSharedPosts/1
	@GetMapping("/GetSharedPosts/{PostId}")
	@ResponseBody
	public List<Post> GetSharedPosts(@PathVariable("PostId") int PostId) {

		List<Post> Posts = PostService.GetSharedPosts(PostId);
		return Posts;
	}
	// http://localhost:8089/Post/getNcomments/1
		@GetMapping("/getNcomments/{PostId}")
		@ResponseBody
	public int getNcomments(@PathVariable("PostId") int PostId) {
			return PostService.getNcomments(PostId);
		}
		
		// http://localhost:8089/Post/getNlikes/1
		@GetMapping("/getNlikes/{PostId}")
		@ResponseBody
	public int getNlikes(@PathVariable("PostId") int PostId) {
			return PostService.getNlikes(PostId);
		}
		
		// http://localhost:8089/Post/getNshares/1
		@GetMapping("/getNshares/{PostId}")
		@ResponseBody
	public int getNshares(@PathVariable("PostId") int PostId) {
			return PostService.getNshares(PostId);
		}
	
	
	// http://localhost:8089/Post/getAllPosts
	@GetMapping("/getAllPosts")
	@ResponseBody
	public List<Post> getAllPosts() {

		List<Post> Posts = PostService.getAllPosts();
		return Posts;

	}
	// http://localhost:8089/Post/delete/1

	@DeleteMapping("/delete/{id}")
	public void deletePost(@PathVariable("id") int PostId) {

		PostService.deletePost(PostId);		
	}
	// http://localhost:8089/Post/update

	@PutMapping("/update")
	public void updatePost(@RequestBody Post Post) {
		PostService.updatePost(Post);
	}
	
	//http://localhost:8089/Post/getforbidden
    @GetMapping("/getforbidden")
    @ResponseBody
    public String getFobiddenWords() {
        return PostService.getForbiddenWords();
    }
    //http://localhost:8089/Post/setforbidden
    @PostMapping("/setforbidden")
    @ResponseBody
    public void setForbiddenWords(@RequestBody String forbiddenWords) {
        if (forbiddenWords==null)
            forbiddenWords="";
        PostService.setForbiddenWords(forbiddenWords);
    }
	// http://localhost:8089/Post/createPostForbidden/1

	@PutMapping("createPostForbidden/{userId}")
	@ResponseBody
	public String createPostForbidden(@RequestBody Post Post,@PathVariable("userId") int userId) {
		return PostService.createPostForbidden(Post,userId);
	}
	
	
	// http://localhost:8089/Post/getAllPosts
	@GetMapping("/getPostById/{postId}")
	@ResponseBody
	public Post getPostById(@PathVariable("postId") int postId) {
		
		Post post = PostService.getPostById(postId);
		System.out.println("it works :)"+postId);
		return post;

	}
	
	    @GetMapping("/getPhotoByPost/{postId}")
		@ResponseBody
		public  ResponseEntity<byte[]> getPhotoByPost(@PathVariable("postId") int postId) throws IOException {
		final ResponseEntity<byte[]> dbImage = PostService.getPhotoByPost(postId);
		return dbImage;
			
		}
		
		// http://localhost:8089/Post/GetMyPosts/1
		@GetMapping("/GetMyPosts/{UserId}")
		@ResponseBody
		public List<Post> GetMyPosts(@PathVariable("UserId") int UserId){
			 return	PostService.GetMyPosts(UserId);
		}
}