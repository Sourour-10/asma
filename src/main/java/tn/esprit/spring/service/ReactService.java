package tn.esprit.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.repository.ReactRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.CommentRepository;
import tn.esprit.spring.repository.PostRepository;
import tn.esprit.spring.IService.IReactService;
import tn.esprit.spring.entity.Comment;
import tn.esprit.spring.entity.Post;
import tn.esprit.spring.entity.React;
import tn.esprit.spring.entity.Reaction;
import tn.esprit.spring.entity.Response;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.ResponseRepository;

@Service
public class ReactService implements IReactService {
	@Autowired
	ResponseRepository responseRepository;
	@Autowired
	ReactRepository reactRepository;
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	ReactRepository ReactRepository;
	
	@Autowired
	PostRepository PostRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CommentRepository commentRepository;
	
	@Override
	@Transactional
	public void addReactToResponse(int reaction, int ResponseId, int UserId) {

		React react = new React();
		Response response = responseRepository.findById(ResponseId).orElse(null);
		User user = userRepository.findById(UserId).orElse(null);
		Reaction myVar = Reaction.LIKE;

		switch (reaction) {
		case 0:
			myVar = Reaction.LIKE;

			break;

		case 1:
			myVar = Reaction.ADORE;

			break;
		case 2:
			myVar = Reaction.HAHA;

			break;
		case 3:
			myVar = Reaction.WOW;
			break;
		case 4:
			myVar = Reaction.CRY;
			break;
		case 5:
			myVar = Reaction.ANGRY;
			break;
		}

		if (reactRepository.findReactByResponseLikeAndUserLike(UserId, ResponseId) == null) {
			System.out.println("Ancien react");
			react.setReaction(myVar);
			react.setResponseLike(response);
			react.setUserLike(user);
			reactRepository.save(react);
			response.setNlikes(response.getNlikes() + 1);

			if (response.getLikes().isEmpty()) {
				List<React> list = new ArrayList<React>();
				list.add(react);
			} else {
				List<React> listr = (List<React>) response.getLikes();
				listr.add(react);
			}
			responseRepository.save(response);

		}

		else {

			if (reactRepository.findReactByResponseLikeAndUserLike(UserId, ResponseId).getReaction() == myVar) {
				reactRepository.delete(reactRepository.findReactByResponseLikeAndUserLike(UserId, ResponseId));
				Response r = responseRepository.findById(ResponseId).orElse(null);
				r.setNlikes(r.getNlikes() - 1);
			} else {
				reactRepository.findReactByResponseLikeAndUserLike(UserId, ResponseId).setReaction(myVar);
			}
		}

	}

	
	
	public boolean Decision(int PostId, int UserId) {
		// true =dislike
		// false like
		User userConnected = userRepository.findById(UserId).orElse(null);
		Post post = PostRepository.findById(PostId).orElse(null);
		List<React> userlikes = userConnected.getLikes();
		List<React> postlikes = post.getLikes();
		boolean liked = false;
		if (!(userlikes.isEmpty())) {
			for (React ru : userlikes) {
				if (postlikes.contains(ru)) {
					liked = true;
				} else
					liked = false;
			}
		}
		return liked;
	}

	public Reaction getReactionByNumber(int reaction) {
		Reaction myVar = Reaction.LIKE;
		switch (reaction) {
		case 0:
			myVar = Reaction.LIKE;
			break;
		case 1:
			myVar = Reaction.ADORE;
			break;
		case 2:
			myVar = Reaction.HAHA;
			break;
		case 3:
			myVar = Reaction.WOW;
			break;
		case 4:
			myVar = Reaction.CRY;
			break;
		case 5:
			myVar = Reaction.ANGRY;
			break;
		}
		return myVar;
	}
	@Override
	public void LikeDislikePost(int reaction, int PostId, int UserId) {
		User userConnected = userRepository.findById(UserId).orElse(null);
		Post post = PostRepository.findById(PostId).orElse(null);
		boolean liked = false;
		List<React> userlikes = userConnected.getLikes();
		List<React> postlikes = post.getLikes();
		if (Decision(PostId, UserId)) {
			// dislike or modif

			for (React ru : userlikes) {
				if ( ( postlikes.contains(ru) ) && (ru.getReaction().equals(getReactionByNumber(reaction)) ) ) {
					//dislike 
					System.out.println("its a delete   ****************************************");
					int nombre = post.getNlikes();
					nombre = nombre - 1;
					post.setNlikes(nombre);
					postlikes.remove(ru);
					userlikes.remove(ru);
					ReactRepository.delete(ru);
					System.out.println(" deleted succesfully  ****************************************");
					break;
				}
				else if( postlikes.contains(ru) ) {
					//modif de lemoji 
					System.out.println(" changement de l'EMOJI  ****************************************");
					ru.setReaction(getReactionByNumber(reaction));
					ReactRepository.save(ru);
				}
			}
		} else {// like
			System.out.println("its a like******************************************");
			React react = new React();
			ReactRepository.save(react);
			react.setReaction(getReactionByNumber(reaction));
			react.setPostLike(post);
			react.setUserLike(userConnected);
			ReactRepository.save(react);
			userlikes.add(react);
			post.setNlikes(post.getNlikes() + 1);
			postlikes.add(react);
			PostRepository.save(post);
		}
	}

	@Override
	@Transactional
	public void addReactToPost(int reaction, int PostId, int UserId) {
		React react = new React();
		Post post = PostRepository.findById(PostId).orElse(null);
		User user = userRepository.findById(UserId).orElse(null);
		react.setReaction(getReactionByNumber(reaction));
		react.setPostLike(post);
		react.setUserLike(user);
		ReactRepository.save(react);
		post.setNlikes(post.getNlikes() + 1);

		if (post.getLikes().isEmpty()) {
			List<React> list = new ArrayList<React>();
			list.add(react);
		} else {
			List<React> listr = (List<React>) post.getLikes();
			listr.add(react);
		}
		PostRepository.save(post);

	}

	@Override
	@Transactional
	public void addReactToComment(int reaction, int CommentId, int UserId) {
		React react = new React();
		Comment comment = commentRepository.findById(CommentId).orElse(null);
		System.out.print("" + comment.getText());
		User user = userRepository.findById(UserId).orElse(null);
		Reaction myVar = Reaction.LIKE;
		switch (reaction) {
		case 0:
			myVar = Reaction.LIKE;

			break;

		case 1:
			myVar = Reaction.ADORE;

			break;
		case 2:
			myVar = Reaction.HAHA;

			break;
		case 3:
			myVar = Reaction.WOW;
			break;
		case 4:
			myVar = Reaction.CRY;
			break;
		case 5:
			myVar = Reaction.ANGRY;
			break;
		}
		react.setReaction(myVar);
		react.setCommentLike(comment);
		react.setUserLike(user);
		ReactRepository.save(react);
		comment.setNlikes(comment.getNlikes() + 1);

		if (comment.getLikes().isEmpty()) {
			List<React> list = new ArrayList<React>();
			list.add(react);
		} else {
			List<React> listr = (List<React>) comment.getLikes();
			listr.add(react);
		}
		commentRepository.save(comment);

	}

	

	@Override
	@Transactional
	public void DislikePost(int ReactID, int UserId) {
		React react = ReactRepository.findById(ReactID).orElse(null);
		int userreact = react.getUserLike().getUserId();
		User userConnected = userRepository.findById(UserId).orElse(null);
		boolean reacttopost = PostRepository.findById(react.getPostLike().getPostId()).isPresent();
		if ((userConnected.getUserId() == userreact) && (reacttopost)) {
			Post post = PostRepository.findById(react.getPostLike().getPostId()).orElse(null);
			post.setNlikes(post.getNlikes() - 1);
			List<React> list = post.getLikes();
			list.remove(react);
			PostRepository.save(post);
			ReactRepository.delete(react);
		}
	}

	@Override
	@Transactional
	public void DislikeComment(int ReactID, int UserId) {
		React react = ReactRepository.findById(ReactID).orElse(null);
		int userreact = react.getUserLike().getUserId();
		User userConnected = userRepository.findById(UserId).orElse(null);
		boolean reacttocomment = commentRepository.findById(react.getCommentLike().getCommentId()).isPresent();
		if ((userConnected.getUserId() == userreact) && (reacttocomment)) {
			Comment comment = commentRepository.findById(react.getCommentLike().getCommentId()).orElse(null);
			comment.setNlikes(comment.getNlikes() - 1);
			List<React> list = comment.getLikes();
			list.remove(react);
			commentRepository.save(comment);
			ReactRepository.delete(react);
		}

	}

	@Override
	public React getReactonPostByUser(int PostId, int UserId) {
		Post post = PostRepository.findById(PostId).orElse(null);
		User userConnected = userRepository.findById(UserId).orElse(null);
		React myreaction = null;
		List<React> userlikes = userConnected.getLikes();
		List<React> postlikes = post.getLikes();
		if(     !(userlikes.isEmpty()) &&   !(postlikes.isEmpty())   ) {
			
			
			for (React ru : userlikes) {
				if (  postlikes.contains(ru) ){
					myreaction = ru;
					break;
				}} 
			System.out.println("jawek behi  ****************************************");
		}
		
		return myreaction;
	}

	@Override
	public List<React> getReactsByuser(int UserId) {
		User userConnected = userRepository.findById(UserId).orElse(null);
		
		return null;
	}

	
	@Override
	public int responseReacts(int responseId) {
		Response response = responseRepository.findById(responseId).orElse(null);
		List<React> reacts = reactRepository.findResponseReacts(response);
		int a = reacts.size();
		return a;
	}
	
}

