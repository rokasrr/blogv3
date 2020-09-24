package lt.rokas.blog.dto;

import java.time.Instant;
/*
 * Comment dto object.
 */
public class CommentDto {
	private long id;
	private String content;
	private String answer;
	private String username;
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
