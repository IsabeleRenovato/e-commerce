package com.isabelerenovato.ecommerce.services.auth;

import com.isabelerenovato.ecommerce.dto.SignupRequest;
import com.isabelerenovato.ecommerce.dto.UserDto;

public interface AuthService {
	
	UserDto createUser (SignupRequest signupRequest);
	
	Boolean hasUserWithEmail(String email);
}
