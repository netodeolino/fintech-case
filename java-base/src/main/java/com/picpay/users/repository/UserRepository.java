package com.picpay.users.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.picpay.users.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u "
			+ "LEFT JOIN Seller s ON s.user.id = u.id "
			+ "LEFT JOIN Consumer c ON c.user.id = u.id "
			+ "WHERE s.username = ?1 OR c.username = ?1")
	Optional<List<User>> findByUserName(String param);

	Optional<List<User>> findByFullNameStartingWithIgnoreCase(String param);
}
