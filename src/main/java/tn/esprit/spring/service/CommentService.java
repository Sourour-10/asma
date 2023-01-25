package tn.esprit.spring.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.ICommentService;
import tn.esprit.spring.entity.Comment;
import tn.esprit.spring.entity.Post;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.CommentRepository;
import tn.esprit.spring.repository.PostRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.entity.utils.PagingHeaders;
import tn.esprit.spring.entity.utils.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class CommentService  implements ICommentService{
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;

	@Override
	public void createComment(Comment comment,int userId) {
		  User user = userRepository.findById(userId).orElse(null);
		  comment.setUser(user);
		  comment.setNlikes(0);
		 
		// TODO Auto-generated method stub
		commentRepository.save(comment);
		
	}

	@Override
	public void deleteComment(int idcomment) {
		// TODO Auto-generated method stub
		
		Comment c = commentRepository.findById(idcomment).orElse(null);
		commentRepository.delete(c);
	}

	@Override
	public void updateComment(Comment comment) {
		// TODO Auto-generated method stub
		commentRepository.save(comment);
	}

	@Override
	public List<Comment> getAllComments() {
		// TODO Auto-generated method stub
		List<Comment> comments = (List<Comment>) commentRepository.findAll();
		return comments;
	}
	//Services 

	@Override
	@Transactional
	public void affectNewCommentToPost(int PostID,int userId, Comment comment) {
		User user = userRepository.findById(userId).orElse(null);
		  comment.setUser(user);
		  System.out.println("the user:"+user.getFirstName());
		Post Post = postRepository.findById(PostID).get();
		comment.setPostCommented(Post);
		System.out.println("the post:"+Post.getText());
		commentRepository.save(comment);
		
		if(Post.getComments().isEmpty())
		{
			List<Comment> list=new ArrayList<Comment>();
		    list.add(comment);
		}
		else {
			List<Comment> Comments = (List<Comment>) Post.getComments();
			Comments.add(comment);	
		}
		//comment.setPost(Post);
		//postRepository.save(Post);
		//commentRepository.save(comment);
		System.out.println("commented");}
	
	
    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }

    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }
    /**
     * get elements using Criteria.
     *
     * @param spec     *
     * @param pageable pagination data
     * @return retrieve elements with pagination
     */
    public PagingResponse get(Specification<Comment> spec, Pageable pageable) {
        Page<Comment> page = commentRepository.findAll(spec, pageable);
        List<Comment> content = page.getContent();
        return new PagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }
    /**
     * get elements using Criteria.
     *
     * @param spec *
     * @return elements
     */
    public List<Comment> get(Specification<Comment> spec, Sort sort) {
        return commentRepository.findAll(spec, sort);
    }
    
	/**
     * get element using Criteria.
     *
     * @param spec    *
     * @param headers pagination data
     * @param sort    sort criteria
     * @return retrieve elements with pagination
     */
    public PagingResponse get(Specification<Comment> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            final List<Comment> entities = get(spec, sort);
            return new PagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }

	@Override
	public void ReplyToComment(int CommentId, String reply) {
		// TODO Auto-generated method stub
		Comment commentParent = commentRepository.findById(CommentId).orElse(null);
		
		Comment Reply = new Comment();
		Reply.setParent(commentParent);
		Reply.setText(reply);
	
			List<Comment> replies = commentParent.getChildren();

			replies.add(Reply);
		commentParent.setChildren(replies);
		Reply.setDate(new Date());
		
		Reply.setUser(commentParent.getUser());
		commentRepository.save(Reply);
	}

	@Override
	public List<Comment> getRepliesByComment(int CommentId) {
		// TODO Auto-generated method stub
		Comment commentParent = commentRepository.findById(CommentId).orElse(null);
		List<Comment> Replies = commentParent.getChildren();
		return Replies;
	}

	@Override
	@Transactional
	public List<Comment> getCommentByPost(int postId) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(postId).orElse(null);
		List<Comment> comments = post.getComments();
	
		//List<Comment> comment  = (List<Comment>) commentRepository.findAll() ;

		return comments;
	}

	@Override
	@Transactional
	public Comment getCommentById(int CommentId) {
		// TODO Auto-generated method stub
		return commentRepository.findById(CommentId).orElse(null);
	}


}
