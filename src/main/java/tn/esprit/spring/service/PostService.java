package tn.esprit.spring.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IPostService;
import tn.esprit.spring.entity.Collaboration;
import tn.esprit.spring.entity.Comment;
import tn.esprit.spring.entity.Photo;
import tn.esprit.spring.entity.Post;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.PhotoRepository;
import tn.esprit.spring.repository.PostRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class PostService implements IPostService{
	
	@Autowired
	PostRepository PostRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PhotoRepository photoRepository;

	@Override
	@Transactional
	public void createPost(Post Post,int userId) {
		// TODO Auto-generated method stub
		User user =  userRepository.findById(userId).orElse(null);
		List<Post> userposts = user.getPosts();
		userposts.add(Post);
		//userRepository.save(user);
		Post.setUser(user);
		Post.setNlikes(0);
		PostRepository.save(Post);
		
	}


	@Override
	public void deletePost(int idPost) {
		// TODO Auto-generated method stub
		
		Post c = PostRepository.findById(idPost).orElse(null);
		PostRepository.delete(c);
		
	}

	@Override
	public void updatePost(Post Post) {
		// TODO Auto-generated method stub
		PostRepository.save(Post);
		
	}

	@Override
	public List<Post> getAllPosts() {
		// TODO Auto-generated method stub
		List<Post> Posts = (List<Post>) PostRepository.findAll();
		return Posts;
	}
	
	@Override
    public String getForbiddenWords(){
       String content="";
        try {
            File file=new ClassPathResource("forbiddenwords.txt").getFile();
            content= new String(Files.readAllBytes(file.toPath()));
            System.out.println(content);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return content;
        
        

	
    }
	@Override
    public void setForbiddenWords(String words) {
        try {
            String filePath ="C:\\Users\\LENOVO\\Desktop\\viwellProject\\src\\main\\resources\\forbiddenwords.txt";
            FileOutputStream f = new FileOutputStream(filePath, true);
            String lineToAppend = getForbiddenWords() +"\t"+ words.replaceAll("[\\t\\n\\r]+", " ").replaceAll(" +", " ");
            byte[] byteArr = lineToAppend.getBytes();//converting string into byte array
           // pw.write(getForbiddenWords() + words.replaceAll("[\\t\\n\\r]+", " ").replaceAll(" +", " "));
            f.write(byteArr);
            f.close();   
        } catch(Exception ex) {
            ex.printStackTrace();
        } 
    }
    


	@Override
	public String createPostForbidden(Post Post, int userId) {
		User user =  userRepository.findById(userId).orElse(null);
        String message = "well done your Post Added SuccessFully!";
            String[] forbiddenWords=getForbiddenWords().split(" ");
            boolean forbiden = true;
            PostRepository.save(Post);
            String[] content=Post.getText().split(" ");
            String newwcontent = "";
            myBreakLabel:
            for (String c : content) {
            	System.out.println("kelma is : ."+c+".");
                for (String forbiddenWord : forbiddenWords) {
                	//c.contains(forbiddenWord)
                	System.out.println("zeyda is : ."+forbiddenWord+".");
                	 
                    if (c.equals(forbiddenWord)) {
                    	forbiden = false;
                    	 System.out.println("true"+forbiden);
                    	 message="oops! ,your post contains some forbidden words, try again without any harmful content";
                    	 c.replace(forbiddenWord, "********");
                    	 System.out.println("your post contains some forbidden words "+c);
                    	 
                    	 break myBreakLabel;
                    	 
                    	 //																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																														c.replaceAll(c, "********");
                    	// newwcontent=+ String.join(" ", c);
                    	// newwcontent.concat(String.join(" ", c));  	
                    	 //forbiden = true;
                    }
                }
            }
            if(!forbiden) {
            	PostRepository.delete(Post);
            }
            return message;
	}


	@Override
	public void SharePost(int PostId, int UserId,Post Share) {
		// TODO Auto-generated method stub
		
		Post PostParent = PostRepository.findById(PostId).orElse(null);
		System.out.println("1 post: "+PostParent.getDate());
		User userShare = userRepository.findById(UserId).orElse(null);
		System.out.println("2 user: "+userShare.getFirstName());
		
		
		Share.setDate(new Date());
		Share.setNlikes(0);
		Share.setParent(PostParent);
		System.out.println("3 success: ");
		String text =Share.getText()+"\n"+ userShare.getFirstName()+" "+ userShare.getLastName()+" -"
		+PostParent.getDate()+"\n"+PostParent.getText();
		Share.setText(text);
		Share.setUser(userShare);
		List<Post> shares = PostParent.getChildren();
		shares.add(Share);
		
			Share.setPhoto(PostParent.getPhoto());
		
		PostRepository.save(Share);
		System.out.println("4 done: "+Share.getPostId());
		
	}


	@Override
	public List<Post> GetSharedPosts(int PostId) {
		// TODO Auto-generated method stub
		Post PostParent = PostRepository.findById(PostId).orElse(null);
		List<Post> Shared = PostParent.getChildren();
		return Shared;
		
		
		
	}


	@Override
	public Post getPostById(int id) {
		// TODO Auto-generated method stub
		Post post = PostRepository.findById(id).orElse(null);
		System.out.println("one post");
		return post;
		
	}


	@Override
	public int getNcomments(int PostId) {
		Post post = PostRepository.findById(PostId).orElse(null);
		int nombre =0;
		if(post.getComments().isEmpty())
			nombre =0;
		else
			nombre = post.getComments().size();
		return nombre;
	}


	@Override
	public int getNlikes(int PostId) {
		Post post = PostRepository.findById(PostId).orElse(null);
		int nombre =0;
		if(post.getLikes().isEmpty())
			nombre =0;
		else
			nombre = post.getLikes().size();
		return nombre;
	}


	@Override
	public int getNshares(int PostId) {
		Post post = PostRepository.findById(PostId).orElse(null);
		int nombre =0;
		if(post.getChildren().isEmpty())
			nombre =0;
		else
			nombre = post.getChildren().size();
		return nombre;
	}


	@Override
	public ResponseEntity<byte[]> getPhotoByPost(int postId) {
		
		Post post = PostRepository.findById(postId).orElse(null);
		Long idImage = post.getPhoto().getId();
		final Optional<Photo> dbImage = photoRepository.findById(idImage);

		return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType()))
				.body(dbImage.get().getImage());
	}


	@Override
	public List<Post> GetMyPosts(int UserId) {
		// TODO Auto-generated method stub
		User user =  userRepository.findById(UserId).orElse(null);
		List<Post> userposts = user.getPosts();
		return userposts;
	}

}
