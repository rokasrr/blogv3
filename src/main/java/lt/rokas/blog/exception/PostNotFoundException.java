package lt.rokas.blog.exception;
/*
 * Throw this exception when post not found in repository.
 */
public class PostNotFoundException extends RuntimeException {
public PostNotFoundException(String a) {
	super(a);
}
}
