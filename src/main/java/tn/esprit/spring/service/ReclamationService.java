package tn.esprit.spring.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IReclamationService;
import tn.esprit.spring.entity.Comment;
import tn.esprit.spring.entity.ComplaintSubjects;
import tn.esprit.spring.entity.Event;
import tn.esprit.spring.entity.Post;
import tn.esprit.spring.entity.React;
import tn.esprit.spring.entity.Reaction;
import tn.esprit.spring.entity.Reclamation;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.ReclamationRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.PostRepository;
import tn.esprit.spring.repository.CommentRepository;
import tn.esprit.spring.repository.EventRepository;

import java.util.stream.Collectors; 

@Service
public class ReclamationService implements IReclamationService{
	
	@Autowired
	ReclamationRepository reclamationReposiory;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository PostRepository;
	@Autowired
	CommentRepository CommentRepository;
	@Autowired
	EventRepository EventRepository;
	@Autowired
	private EmailSenderService EmailSenderService;

	@Override
	public void createReclamation(int UserId,int num,String complaintSubject ,String Content) {
		Reclamation Reclamation = new Reclamation();
		Reclamation.setContent(Content);
		Reclamation.setNum(num);
		// TODO Auto-generated method stub
		Reclamation.setDate(new Date());

		ComplaintSubjects myVar = ComplaintSubjects.ReportPost;
		String Var = "";
		
	    switch(complaintSubject) {
	      case "ReportPost":
	    	  myVar = ComplaintSubjects.ReportPost;
	    	  Var = "Post";
	    	
	        break;
	      
	      case "ReportComment":
	    	   myVar = ComplaintSubjects.ReportComment;
	    	   Var = "Comment";
	        break;
	      case "ReportEvent":
	    	  myVar = ComplaintSubjects.ReportEvent;
	    	  Var = "Event";
	        break;
	      case "ReportProfile":
	    	  myVar = ComplaintSubjects.ReportProfile;
	    	  Var = "Profile";
		        break;
	    }
	    Reclamation.setComplaintSubject(myVar);
	    Reclamation.setState(false);
	    User user = userRepository.findById(UserId).orElse(null);
	    Reclamation.setUser(user);
	    Set<Reclamation> userReclamations = (Set<Reclamation>) user.getReclamations();
	    userReclamations.add(Reclamation);
	    
	    
	    reclamationReposiory.save(Reclamation);
	    
	    String toEmail = user.getMail();
	    String body = "we receive your Complaint about the "+Var+" as you said that is: "+Reclamation.getContent()+" ."+"\\n"+" we will take your Complaint into consideration and make the necessary modifications";
	    String Subject="Complaint to "+Var+" Received";
	    //(String toEmail, String subject, String body)
	  //  EmailSenderService.sendEmail(toEmail, Subject, body);
	}

	@Override
	public void deleteReclamation(int idreclamation) {
		// TODO Auto-generated method stub
		Reclamation c = reclamationReposiory.findById(idreclamation).orElse(null);
		reclamationReposiory.delete(c);
	}

	@Override
	public void updateReclamation(Reclamation Reclamation) {
		// TODO Auto-generated method stub
		Reclamation.setDate(new Date());
		reclamationReposiory.save(Reclamation);
		
	}

	@Override
	public List<Reclamation> getAllReclamations() {
		// TODO Auto-generated method stub
		List<Reclamation> Reclamations = (List<Reclamation>) reclamationReposiory.findAll();
		return Reclamations;
	}
	
	 public Integer[] MaxOccur(ArrayList<Integer> array){
		 int max = 0;
		 int pos = 0;
		 Integer[] MaxAndPosition = {0,0};
		 
			 for( int i = 0; i < array.size(); i++) {
		        int count = 0;
		       
		        for( int j = 0; j < array.size(); j++){
		          if (array.get(i)==array.get(j) )
		              count++;
		    }
		   if (count >= max)
		       max = count;
		   pos = array.get(i);
		  }
			 MaxAndPosition[0]= max;
			 MaxAndPosition[1]= pos;
			return MaxAndPosition;
		 
	 }

	@Override
	public void complaintsSystemDecision(int UserId) {
		String toEmail = "";
		String Subject =" ";
		String body = "";
		User UserDecision= null ;
		User UserAdmin = userRepository.findById(UserId).orElse(null);
		Post PostDecision = null;
		Comment CommentDecision= null;
		Event EventDecision= null;
		//1:All Reclamations
		List<Reclamation> AllReclamations = new ArrayList<Reclamation>();
		AllReclamations = getAllReclamations();
		
		List<Reclamation> ReclamationsPosts = new ArrayList<Reclamation>();
		ArrayList<Integer> RecPostsID = new ArrayList<Integer>();
		List<Reclamation> ReclamationsComments = new ArrayList<Reclamation>();
		ArrayList<Integer> RecCommID = new ArrayList<Integer>();
		List<Reclamation> ReclamationsEvent = new ArrayList<Reclamation>();
		ArrayList<Integer> RecEventsID = new ArrayList<Integer>();
		List<Reclamation> ReclamationsProfile = new ArrayList<Reclamation>();
		ArrayList<Integer> RecProfileID = new ArrayList<Integer>();
		//2:Classer les rec selon leur sujet + les ID du post/comm/event/user
		int i = 0;
		for(Reclamation r : AllReclamations) {
			//Non Traiter 
			if(r.getState().equals(false)) {
				if( r.getComplaintSubject().equals(ComplaintSubjects.ReportPost)  ) {
					ReclamationsPosts.add(r);
					 
					RecPostsID.add(r.getNum());
					}
				else if ( r.getComplaintSubject().equals(ComplaintSubjects.ReportComment)) {
					ReclamationsComments.add(r);
					
					 RecCommID.add(r.getNum());}
				else if( r.getComplaintSubject().equals(ComplaintSubjects.ReportEvent) ) {
					ReclamationsEvent.add(r);
					
					 RecEventsID.add(r.getNum());}
				else if( r.getComplaintSubject().equals(ComplaintSubjects.ReportProfile) ) {
					ReclamationsProfile.add(r);
					
					RecProfileID.add(r.getNum());
					}
				}
		}
		//3:  max redundant element from list
		Integer[] MaxAndPositionPosts = null;
		Integer[] MaxAndPositionComm= null;
		Integer[] MaxAndPositionEvents= null;
		Integer[] MaxAndPositionProfile= null;
		if(! (RecPostsID.isEmpty()) ) {
			//ID du Post le
			 MaxAndPositionPosts = MaxOccur(RecPostsID);
		}
		if(! (RecCommID.isEmpty()) ) {
			 MaxAndPositionComm = MaxOccur(RecCommID);}
		if(! (RecEventsID.isEmpty()) ) {
			 MaxAndPositionEvents = MaxOccur(RecEventsID);}
		if(! (RecProfileID.isEmpty()) ) {
			 MaxAndPositionProfile = MaxOccur(RecProfileID);}
		
		
		//4:Traiter ReportPost
		if(MaxAndPositionPosts != null) {
			if( MaxAndPositionPosts[0] > 5) {
				System.out.println("tu dois effacer ce post d'id: " + MaxAndPositionPosts[1]);
			PostDecision = PostRepository.findById(MaxAndPositionPosts[1]).orElse(PostDecision);
			UserDecision = PostDecision.getUser();
			if(UserDecision != null) {
				toEmail = UserDecision.getMail();
				Subject = "Post Complaint ";
				body = "Hi Mr "+UserDecision.getFirstName()+" ,we receive more than 5 Report to your Post which is: "+PostDecision.getText()+". so we advice you to delete it";
				//(String toEmail, String Subject, String body)
			    EmailSenderService.sendEmail(toEmail, Subject, body);
			}
			System.out.println(UserDecision.getFirstName());

				//Changer ces 6 reclamation en traité
				for(Reclamation rp : ReclamationsPosts) {
					rp.setState(true);
					reclamationReposiory.save(rp);
				}
			}
		}

		//5:Traiter ReportComment
		if(MaxAndPositionComm != null) {
			if( MaxAndPositionComm[0] > 2) {
				System.out.println("hayya nchoufou" +MaxAndPositionComm[0]+"tawa nchoufou poss" +MaxAndPositionComm[1]);
				System.out.println("tu dois effacer ce comment d'id: " + MaxAndPositionComm[1]);
				CommentDecision = CommentRepository.findById(MaxAndPositionComm[1]).orElse(CommentDecision);
				UserDecision = CommentDecision.getUser();
				if(UserDecision != null) {
					toEmail = UserDecision.getMail();
					Subject = "Comment Complaint ";
					body = "Hi Mr "+UserDecision.getFirstName()+" ,we receive more than 2 Report to your Comment which is: "+CommentDecision.getText()+". Thats why we decide to delete it";
					//(String toEmail, String Subject, String body)
				    //EmailSenderService.sendEmail(toEmail, Subject, body);
				    CommentRepository.delete(CommentDecision);
				    
				}
				//Changer ces 6 reclamation en traité
				for(Reclamation rp : ReclamationsComments) {
					rp.setState(true);
					reclamationReposiory.save(rp);
				}
			}
		}

		//6:Traiter ReportEvent
				if(MaxAndPositionEvents  != null ) {
					if( MaxAndPositionEvents[0] > 3) {
						System.out.println("tu dois effacer ce comment d'id: " + MaxAndPositionEvents[1]);
						EventDecision = EventRepository.findById(MaxAndPositionEvents[1]).orElse(EventDecision);
						//UserAdmin = userRepository.findById(2).orElse(UserAdmin);
						Post NewPost = new Post();
						NewPost.setDate(new Date());
						NewPost.setNlikes(0);
						NewPost.setUser(UserAdmin);
						NewPost.setText("we receive all your complaints about the "+EventDecision.getTitle() + " Event"+" which is have place "+
						EventDecision.getPlace()+" thats why we apologize about those reports and we wish avoid those problems in the next one.");
						PostRepository.save(NewPost);
						//Changer ces 6 reclamation en traité
						for(Reclamation rp : ReclamationsEvent) {
							rp.setState(true);
							reclamationReposiory.save(rp);
						}
					}
				}

		//7:Traiter ReportProfile
				if(MaxAndPositionProfile != null) {
					if( MaxAndPositionProfile[0] > 10) {
						System.out.println("tu dois effacer ce comment d'id: " + MaxAndPositionProfile[1]);
						
						UserDecision = userRepository.findById(MaxAndPositionProfile[1]).orElse(null);
						if(UserDecision != null) {
							toEmail = UserDecision.getMail();
							Subject = "Profile Complaint ";
							body = "Hi Mr "+UserDecision.getFirstName()+" ,we receive more than 10 Report to your Profile so we advice you to communicate with your collegeues trying to get a solution.";
							//(String toEmail, String Subject, String body)
						    EmailSenderService.sendEmail(toEmail, Subject, body);
						}
						
						//Changer ces 6 reclamation en traité
						for(Reclamation rp : ReclamationsProfile) {
							rp.setState(true);
							reclamationReposiory.save(rp);
						}
					}
				}
	}

	@Override
	public Reclamation getReclamationById(int recId) {
		// TODO Auto-generated method stub
		return reclamationReposiory.findById(recId).orElse(null);
	}
	 


}
