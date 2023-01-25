package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Badge;

@Repository
public interface BadgeRepository  extends CrudRepository<Badge,Integer> {

	@Query("Select badgeId from Badge b where b.name= :name")
	public int findBadgeByName(@Param("name") String name);
}
