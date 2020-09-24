package lt.rokas.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lt.rokas.blog.domain.UserRepository;
import lt.rokas.blog.dto.AuthenticationResponse;
import lt.rokas.blog.dto.LoginRequest;
import lt.rokas.blog.dto.RegisterRequest;
import lt.rokas.blog.model.User;
import lt.rokas.blog.security.JwtProvider;

@Service
public class AuthService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtProvider jwtProvider;
	/*
	 * Uses register dto object to map it into user entity and save in repository.
	 */
	public void signup(RegisterRequest registerRequest) {
		
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setEmail(registerRequest.getEmail());
		userRepository.save(user);
	}
	/*
	 * Using login data object check if it is ok.
	 * If ok - returns auth token and username.
	 */
	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String authenticationToken = jwtProvider.generateToken(authenticate);
		return new AuthenticationResponse(loginRequest.getUsername(),authenticationToken);
	}
	/*
	 * Returns current user from context holder.
	 */
	public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return Optional.of(principal);
	}

}
