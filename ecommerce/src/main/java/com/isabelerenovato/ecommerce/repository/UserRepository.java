package com.isabelerenovato.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isabelerenovato.ecommerce.entity.User;
import com.isabelerenovato.ecommerce.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findFirstByEmail(String email);
	
	User findByRole(UserRole userRole);
}
