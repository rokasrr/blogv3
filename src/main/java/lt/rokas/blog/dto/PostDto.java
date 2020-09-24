package lt.rokas.blog.dto;
/*
 * post dto object
 */
public class PostDto {

	private Long id;
	private String content;
	private String title;
	private String username;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String body) {
		this.content = body;
	}

}
