package tn.esprit.spring.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entity.Comment;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer>, JpaSpecificationExecutor<Comment>, CrudRepository<Comment,Integer>{
/*
 * JpaSpecificationExecutor permet d’exécuter les spécifications générées précédemment.
 * PagingAndSortingRepository permet de paginer et de trier les ressources obtenus.
 */
}
