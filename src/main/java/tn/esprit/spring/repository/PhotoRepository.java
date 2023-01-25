package tn.esprit.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Photo;
@Repository
public interface PhotoRepository extends CrudRepository<Photo,Long>{
	Optional<Photo> 	findByName(String name);
	Optional<Photo>  findById (long id);
}
