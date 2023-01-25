package tn.esprit.spring.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Role;
import tn.esprit.spring.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
//Logiin
	User findByUserNameAndPassword(String username, String password);
	
	
	// Anas
	@Query("Select firstName from User order by points desc")
	public List<String> sortEmployeeBypoints();

	@Query("Select firstName from User order by rating desc")
	public List<String> top5Rated();

	
	User findByUserName(String userName);
	
//Optional<User> findByUserName(String userName);

	Boolean existsByUserName(String username);

	Boolean existsByMail(String email);
	
	public User findByMail(String email);
	
    //public List<User> findAllOrderByPointsAsc(); 
	public List<User> findTop3ByOrderByPointsDesc();





}
