package lt.rokas.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
/*
 * Authentication response object. Returning this dto object frontend to store in local storage.
 */
@Data
@AllArgsConstructor
public class AuthenticationResponse {
	private String username;
	private String authenticationToken;
}
