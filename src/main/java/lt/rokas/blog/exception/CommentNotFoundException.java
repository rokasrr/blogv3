package lt.rokas.blog.exception;
/*
 * Throw this exception when comment is not found in repository.
 */
public class CommentNotFoundException extends RuntimeException {
	public CommentNotFoundException(String a) {
		super(a);
	}
}
