package com.isabelerenovato.ecommerce.controller;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isabelerenovato.ecommerce.dto.AuthenticationRequest;
import com.isabelerenovato.ecommerce.dto.SignupRequest;
import com.isabelerenovato.ecommerce.dto.UserDto;
import com.isabelerenovato.ecommerce.entity.User;
import com.isabelerenovato.ecommerce.repository.UserRepository;
import com.isabelerenovato.ecommerce.services.auth.AuthService;
import com.isabelerenovato.ecommerce.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	
	private final UserDetailsService userDetailsService;
	
	private final UserRepository userRepository;
	
	private final JwtUtil jwtUtil;
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String HEADER_STRING = "Authorization";
	
	private final AuthService authService;
	
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
											HttpServletResponse response) throws IOException, JSONException {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
												authenticationRequest.getPassword()));
			
		} catch (BadCredentialsException e){
			throw new BadCredentialsException("Nome de usuário ou senha incorreta.");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		
		if(optionalUser.isPresent()) {
			response.getWriter().write(new JSONObject().put("userId", optionalUser.get().getId()).put("role", optionalUser.get().getRole()).toString());
		}
		
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, " +
		"X-Request-With, Content-Type, Accept, X-Custom-Header");
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
		if(authService.hasUserWithEmail(signupRequest.getEmail())) {
			return new ResponseEntity("Esse usuário já existe", HttpStatus.NOT_ACCEPTABLE);
		}
		
		UserDto userDto = authService.createUser(signupRequest);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
		
	}
}
