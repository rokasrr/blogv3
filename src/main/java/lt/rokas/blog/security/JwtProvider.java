package lt.rokas.blog.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lt.rokas.blog.exception.SpringBlogException;

@Service
public class JwtProvider {
	KeyStore keyStore;

	/*
	 * Initializes keystore
	 */
	@PostConstruct
	public void init() {
		InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
		try {
			keyStore = KeyStore.getInstance("JKS");
			keyStore.load(resourceAsStream, "GerasBus123".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			throw new SpringBlogException("Exception occured while loading keystore");
		}
	}

	/*
	 * Generates new Jwt token
	 */
	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();

	}
	/*
	 * Return private key from keystore
	 * If not - throws custom exception.
	 */
	private PrivateKey getPrivateKey() {
		Key key = null;
		try {
			key = keyStore.getKey("springblog", "GerasBus123".toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new SpringBlogException("Exception occured while loading key from keystore");
		}
		return (PrivateKey) key;
	}
	/*
	 * Checks if token valid our stored private token.
	 */
	public boolean validateToken(String jwt) {
		Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;
	}
	/*
	 * returns stored public key from keyStore
	 */
	private PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("springblog").getPublicKey();
		} catch (KeyStoreException e) {
			throw new SpringBlogException("Exception occured while loading public key from keystore");
		}
		 
	}
	/*
	 * Parse username from Jwt token.
	 */
	public String getUsernameFromJwt(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt).getBody();
		return claims.getSubject();
	}
}
