package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.React;

public interface IReactService {

	public void addReactToPost(int reaction, int PostId, int UserId);

	public void addReactToComment(int reaction, int CommentId, int UserId);

	public void DislikePost(int ReactID, int UserId);

	public void DislikeComment(int ReactID, int UserId);

	public void LikeDislikePost(int reaction,int PostId,int UserId);
	public React getReactonPostByUser(int PostId,int UserId);
	public List<React> getReactsByuser(int UserId);

	// Ramzi
	void addReactToResponse(int reaction, int ResponseId, int UserId);
	
	public int responseReacts(int responseId);

}
