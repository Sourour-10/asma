package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Training;

@Repository

public interface TrainingRepository extends CrudRepository<Training, Integer> {

}
