package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.IService.ICommentService;
import tn.esprit.spring.IService.IPostService;
import tn.esprit.spring.entity.Comment;
import tn.esprit.spring.entity.utils.PagingHeaders;
import tn.esprit.spring.entity.utils.PagingResponse;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/Comment")
public class CommentController {
	@Autowired
	ICommentService CommentService;

	
	
	// http://localhost:8089/Comment/getAllComments
	@GetMapping("/getCommentByPost/{postId}")
	@ResponseBody
	public List<Comment> getCommentByPost(@PathVariable("postId") int postId) {
		List<Comment> Comments = CommentService.getCommentByPost(postId);
		System.out.println("hhhh");
		return Comments;
	}
	
	// http://localhost:8089/Comment/create/1

	@PostMapping("create/{userId}")
	@ResponseBody
	public void createComment(@RequestBody Comment Comment,@PathVariable("userId") int userId) {
		CommentService.createComment(Comment, userId);
	}

	// http://localhost:8089/Comment/getAllComments
	@GetMapping("/getAllComments")
	@ResponseBody
	public List<Comment> getAllComments() {

		List<Comment> Comments = CommentService.getAllComments();
		return Comments;

	}
	

	
	// http://localhost:8089/Badge/delete/1

	@DeleteMapping("/delete/{id}")
	public void deleteComment(@PathVariable("id") int CommentId) {

		CommentService.deleteComment(CommentId);		
	}
	// http://localhost:8089/Badge/update/1

	@PutMapping("/update")
	public void updateComment(@RequestBody Comment Comment) {
		CommentService.updateComment(Comment);
	}
	
	//Services
	// http://localhost:8089/Comment/NewCommentToPost/1/1

	@PutMapping("NewCommentToPost/{PostID}/{userId}")
	@ResponseBody
	public void affectNewCommentToPost(@PathVariable("PostID") int PostID,@PathVariable("userId") int userId, @RequestBody Comment Comment) {
		CommentService.affectNewCommentToPost(PostID, userId, Comment);
	}
	
    public HttpHeaders returnHttpHeaders(PagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
    
	
	@Transactional
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Comment>> get(
	        @And({
	                @Spec(path = "text", params = "text", spec = Like.class),
	                //@Spec(path = "user", params = "user", spec = Like.class),
	               // @Spec(path = "PostCommented", params = "PostCommented", spec = In.class),
	                @Spec(path = "Nlikes", params = "Nlikes", spec = Equal.class),
	                @Spec(path = "date", params = "date", spec = Equal.class),
	                @Spec(path = "date", params = {"createDateGt", "createDateLt"}, spec = Between.class)
	        }) Specification<Comment> spec,
	        Sort sort,
	        @RequestHeader HttpHeaders headers) {
	    final PagingResponse response = CommentService.get(spec, headers, sort);
	    return new ResponseEntity<>(response.getElements(), returnHttpHeaders(response), HttpStatus.OK);
	}
	
	

	// http://localhost:8089/SpringMVC/Comment/ReplyToComment/1
	@PostMapping("ReplyToComment/{CommentId}")
	@ResponseBody
	public void ReplyToComment(@PathVariable("CommentId") int CommentId,@RequestBody String reply) {
		CommentService.ReplyToComment( CommentId,  reply);
	}
	
	// http://localhost:8089/SpringMVC/Comment/getRepliesByComment
	@GetMapping("/getRepliesByComment/{CommentId}")
	@ResponseBody
	public List<Comment> getRepliesByComment(@PathVariable("CommentId") int CommentId) {

		List<Comment> Comments = CommentService.getRepliesByComment(CommentId);
		return Comments;

	}
	
	// http://localhost:8089/SpringMVC/Comment/getRepliesByComment
	@GetMapping("/getCommentById/{CommentId}")
	@ResponseBody
	public Comment getCommentById(@PathVariable("CommentId") int CommentId) {
		return CommentService.getCommentById(CommentId);
		
	}
}


