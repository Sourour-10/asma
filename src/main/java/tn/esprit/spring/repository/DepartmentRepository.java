package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.spring.entity.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {

}
