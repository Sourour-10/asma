package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import tn.esprit.spring.IService.IReactService;
import tn.esprit.spring.entity.Comment;
import tn.esprit.spring.entity.React;
import tn.esprit.spring.entity.Reaction;

@RestController
@RequestMapping("/React")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ReactController {
	@Autowired
	IReactService ReactService;

	// http://localhost:8089/React/create/2/1/1
	@PutMapping("ReactToResponse/{reactionId}/{ResponseId}/{UserId}")
	@ResponseBody
	public void addReactToResponse(@PathVariable("reactionId") int reaction, @PathVariable("ResponseId") int ResponseId,
			@PathVariable("UserId") int UserId) {
		ReactService.addReactToResponse(reaction, ResponseId, UserId);
	}

	@PutMapping("ReactToPost/{reactionId}/{PostId}/{UserId}")
	@ResponseBody
	public void addReactToPost(@PathVariable("reactionId") int reaction, @PathVariable("PostId") int PostId,
			@PathVariable("UserId") int UserId) {
		ReactService.addReactToPost(reaction, PostId, UserId);
	}

	// http://localhost:8089/React/create/2/1/1
	@PutMapping("ReactToComment/{reactionId}/{CommentId}/{UserId}")
	@ResponseBody
	public void addReactToComment(@PathVariable("reactionId") int reaction, @PathVariable("CommentId") int CommentId,
			@PathVariable("UserId") int UserId) {
		ReactService.addReactToComment(reaction, CommentId, UserId);
	}

	// http://localhost:8089/React/DislikePost/1/1
	@DeleteMapping("/DislikePost/{ReactID}/{UserId}")
	public void DislikePost(@PathVariable("ReactID") int ReactID, @PathVariable("UserId") int UserId) {
		ReactService.DislikePost(ReactID, UserId);
	}

	// http://localhost:8089/React/DislikeComment/1/1
	@DeleteMapping("/DislikeComment/{ReactID}/{UserId}")
	public void DislikeComment(@PathVariable("ReactID") int ReactID, @PathVariable("UserId") int UserId) {
		ReactService.DislikeComment(ReactID, UserId);
	}

	// http://localhost:8089/React/ReactToPost1/2/1/1

	@PutMapping("LikeDislikePost/{reactionId}/{PostId}/{UserId}")
	public void LikeDislikePost(@PathVariable("reactionId") int reaction, @PathVariable("PostId") int PostId,
			@PathVariable("UserId") int UserId) {
		ReactService.LikeDislikePost(reaction, PostId, UserId);
	}
	
	// http://localhost:8089/React/getReactonPostByUser/1/1
		@GetMapping("getReactonPostByUser/{PostId}/{UserId}")
		@ResponseBody()
	public String getReactonPostByUser(@PathVariable("PostId")int PostId,@PathVariable("UserId") int UserId) {
		React r =	ReactService.getReactonPostByUser( PostId,  UserId);
		String strreact = "NoReaction";
		if(r != null) {
			
			 strreact = ""+r.getReaction();
		}
		System.out.println(strreact);
		return strreact;
		 
	}

	// http://localhost:8089/React/ResponseReacts/1
		@GetMapping("/ResponseReacts/{responseId}")
		public void ResponseReacts(@PathVariable("responseId") int responseId) {
			ReactService.responseReacts(responseId);

		
		}
}