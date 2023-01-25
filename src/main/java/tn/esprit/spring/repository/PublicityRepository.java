package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import tn.esprit.spring.entity.Publicity;

@Repository
public interface PublicityRepository  extends CrudRepository<Publicity,Integer> {


}
