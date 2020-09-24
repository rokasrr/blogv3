package lt.rokas.blog.service;

import java.lang.module.FindException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import lt.rokas.blog.domain.CommentRepository;
import lt.rokas.blog.domain.PostRepository;
import lt.rokas.blog.dto.CommentDto;
import lt.rokas.blog.dto.PostDto;
import lt.rokas.blog.exception.CommentNotFoundException;
import lt.rokas.blog.exception.PostNotFoundException;
import lt.rokas.blog.model.Comment;
import lt.rokas.blog.model.Post;

@Service
public class PostService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	AuthService authService;
	/*
	 * Method used to create new post from post dto object and save it to repository.
	 */
	public void createPost(PostDto postDto) {
		Post post = mapFromDtoToPost(postDto);
		postRepository.save(post);
	}
	/*
	 * Maps postdto object to Post entity.
	 */
	private Post mapFromDtoToPost(PostDto postDto) {
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		User username = authService.getCurrentUser().orElseThrow(()->new IllegalArgumentException("No user logged in"));
		post.setUsername(username.getUsername());
		post.setCreated(Instant.now());
		return post;
	}
	/*
	 * Get all posts from post repository and return as a list.
	 */
	public List<PostDto> showAllPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream().map(this::mapFromPostToDto).collect(Collectors.toList());
	}
	/*
	 * Map post entity to postdto object.
	 */
	public PostDto mapFromPostToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setContent(post.getContent());
		postDto.setUsername(post.getUsername());
		return postDto;
	}
	/*
	 * Returns single postdto object of postrepository and maps it from post object.
	 * need post id to proceed.
	 */
	public PostDto showSinglePost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id "+id));
		return mapFromPostToDto(post);
	}
	/*
	 * Service method to delete single post by using post ID.
	 */
	public void deletePost(Long id) {
	postRepository.deleteById(id);
		System.out.println("Istrinta + "+id);
	}
	/*
	 * Update post method. Receiving post dto object. 
	 * After receiving post entity from repository updates it with current dto data and save it again.
	 * throws post not found exception if repository doesnt have same id object.
	 */
	public void updatePost(PostDto postDto) {
		if(this.postRepository.findById(postDto.getId()).isPresent()) {
			Post existingPost = this.postRepository.findById(postDto.getId()).orElseThrow(() -> new PostNotFoundException("For id " +postDto.getId()));
			existingPost.setTitle(postDto.getTitle());
			existingPost.setContent(postDto.getContent());
			existingPost.setUpdated(Instant.now());
			this.postRepository.save(existingPost);
		}
		
		
	}
	/*
	 * Service method used to save new comment object into repository.
	 * REceives comment dto object and id of post to connect.
	 */
	public void commentPost(CommentDto commentDto, Long id) {
		Comment comment = mapFromDtoToComment(commentDto);
		if(comment.getUsername()==null) {
			comment.setUsername("Anonymous");
		}
		Post existingPost = this.postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " +id));
		existingPost.getComments().add(comment);
		postRepository.save(existingPost);
	}
/*
 * Mapping object from Dto to Comment entity obj.
 */
	private Comment mapFromDtoToComment(CommentDto commentDto) {
		Comment comment = new Comment();
		comment.setAnswer(commentDto.getAnswer());
		comment.setContent(commentDto.getContent());
//		User username = authService.getCurrentUser().orElseThrow(()->new IllegalArgumentException("No user logged in")); Isimtas, bet veikia.
//		comment.setUsername(username.getUsername()); Works fine but turned of because of frondend bug.
		comment.setCreated(Instant.now());
		return comment;
	}
	/*
	 * Method used when we need to map from comment dto comment dto object.
	 * Receiving comment object as a parameter.
	 */
	private CommentDto mapFromCommentToDto(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setAnswer(comment.getAnswer());
		commentDto.setContent(comment.getContent());
		commentDto.setUsername(comment.getUsername()); 
		return commentDto;
	}

	/*
	 * Service method used to receive all comments using Post id.
	 * Return list as a object.
	 */
	public List<CommentDto> showAllComments(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id "+id));
		List<Comment> comments = post.getComments();
		return comments.stream().map(this::mapFromCommentToDto).collect(Collectors.toList());
	}

	/*
	 * Find and return object using its id.
	 */
	public CommentDto showComment(Long id) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("For id "+id));
		return mapFromCommentToDto(comment);
	}

	/*
	 * Find and update existing object using comment dto object from parameters.
	 */
	public void updateComment(CommentDto commentDto) {
		Comment newComment = mapFromDtoToComment(commentDto);
		if(this.commentRepository.findById(commentDto.getId()).isPresent()) {
			Comment existingComment = this.commentRepository.findById(commentDto.getId()).orElseThrow(() -> new CommentNotFoundException("For id " +commentDto.getId()));
			existingComment.setAnswer(newComment.getAnswer());
			existingComment.setContent(newComment.getContent());
			this.commentRepository.save(existingComment);
		}
	}

	/*
	 * Deletes post using comment id and post dto object to get Post object from which one comment have to be deleted.
	 */
	public void deleteComment(Long id, PostDto postDto) {
	Post existingPost = postRepository.findById(postDto.getId()).orElseThrow(() -> new PostNotFoundException("For id: "+postDto.getId()));
	List<Comment> existingList = existingPost.getComments();
	existingList.removeIf(e->e.getId()==id);
	System.out.println("Deleted comment id: "+id);
	postRepository.save(existingPost);
	}
	}



