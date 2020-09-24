package lt.rokas.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.rokas.blog.dto.AuthenticationResponse;
import lt.rokas.blog.dto.LoginRequest;
import lt.rokas.blog.dto.RegisterRequest;
import lt.rokas.blog.service.AuthService;
/*
 * Our authentication controller.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	/*
	 * 
	 */
	@Autowired
	AuthService authService;
	/*
	 * Waiting for register request and forward it to authentication service to proceed.
	 * 
	 * 
	 * 
	 */
	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return new ResponseEntity(HttpStatus.OK);
	}
	/*
	 * Waiting for login request and forwards it to authentication service to proceed.
	 */
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
	return authService.login(loginRequest);
	}
 
}
