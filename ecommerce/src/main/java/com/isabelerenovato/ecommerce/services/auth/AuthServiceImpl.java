package com.isabelerenovato.ecommerce.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.isabelerenovato.ecommerce.dto.SignupRequest;
import com.isabelerenovato.ecommerce.dto.UserDto;
import com.isabelerenovato.ecommerce.entity.User;
import com.isabelerenovato.ecommerce.enums.UserRole;
import com.isabelerenovato.ecommerce.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserDto createUser(SignupRequest signupRequest) {
		User user = new User();
		
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setRole(UserRole.CUSTOMER);
		User createUser = userRepository.save(user);
		
		UserDto userDto = new UserDto();
		userDto.setId(createUser.getId());
		
		return userDto;
	
	}
	
	public Boolean hasUserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}
	
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByRole(UserRole.ADMIN);
		if(null == adminAccount) {
			User user = new User();
			user.setEmail("admin@test.com");
			user.setName("Admin");
			user.setRole(UserRole.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}
}
