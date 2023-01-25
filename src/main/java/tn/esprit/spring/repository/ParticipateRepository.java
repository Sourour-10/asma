package tn.esprit.spring.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import antlr.debug.Event;
import tn.esprit.spring.entity.Participate;
import tn.esprit.spring.entity.Type;
import tn.esprit.spring.entity.User;
@Repository
public interface ParticipateRepository  extends CrudRepository<Participate,Integer> {
	
	List<Participate> findByType(Type type);
	

}
