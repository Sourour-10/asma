package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Coach;

@Repository
public interface CoachRepository extends CrudRepository<Coach,Integer> {

}
