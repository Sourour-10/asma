package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Poll;

@Repository
public interface PollRepository extends CrudRepository<Poll, Integer>{

}
