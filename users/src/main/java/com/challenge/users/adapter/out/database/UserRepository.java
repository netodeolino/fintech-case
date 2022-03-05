package com.challenge.users.adapter.out.database;

import java.util.List;
import java.util.Optional;

import com.challenge.users.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u "
			+ "LEFT JOIN Seller s ON s.user.id = u.id "
			+ "LEFT JOIN Consumer c ON c.user.id = u.id "
			+ "WHERE s.username = ?1 OR c.username = ?1")
	Optional<List<User>> findByUserName(String param);

	Optional<List<User>> findByFullNameStartingWithIgnoreCase(String param);

}
