package lt.rokas.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.rokas.blog.dto.CommentDto;
import lt.rokas.blog.dto.PostDto;
import lt.rokas.blog.service.PostService;

@RestController
@RequestMapping("/api/posts/")
public class PostController {
	
	@Autowired
	PostService postService;
	/*
	 * Mapping waiting for new post object to save it.
	 * Should be sent as PostDto object.
	 */
	@PostMapping("/post")
	public ResponseEntity createPost(@RequestBody PostDto postDto) {
		postService.createPost(postDto);
		return new ResponseEntity(HttpStatus.OK);
	}
	/*
	 * Mapping used when we need to delete post object. 
	 * Requesting id from path. Must be logged in as admin.
	 */
	@PostMapping("/delete/{id}")
	public ResponseEntity deletePost(@PathVariable @RequestBody Long id) {
		postService.deletePost(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	/*
	 * Using path variable to delete comment and postDto object to know from which one post it is.
	 */
	@PostMapping("/deletecomment/{id}")
	public ResponseEntity deleteComment(@PathVariable @RequestBody Long id, @RequestBody PostDto postDto) {
		postService.deleteComment(id, postDto);
		return new ResponseEntity(HttpStatus.OK);
	}
	/*
	 * Waiting for updated postDto object to update existing post object.
	 * Should be sent as PostDto obj.
	 */
	@PostMapping("/update/{id}")
	public ResponseEntity updatePost(@RequestBody PostDto postDto) {
		postService.updatePost(postDto);
		return new ResponseEntity(HttpStatus.OK);
	}
	/*
	 * Mapping to add new comment for existing post
	 */
	@PostMapping("/comment/{id}")
	public ResponseEntity commentPost(@PathVariable @RequestBody Long id, @RequestBody CommentDto commentDto) {
		postService.commentPost(commentDto, id);
		return new ResponseEntity(HttpStatus.OK);
	}
	/*
	 * Edit comment function
	 * Receiving edited commentdto object
	 */
	@PostMapping("/updatecomment")
	public ResponseEntity updateComment(@RequestBody CommentDto commentDto){
		postService.updateComment(commentDto);
		return new ResponseEntity(HttpStatus.OK);
	}
	/*
	 * Mapping used to get all existing post from database using service.
	 */
	@GetMapping("/all")
	public ResponseEntity<List<PostDto>> showAllPosts(){
		
		return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
	}
	/*
	 * Receive all comments using Post id. 
	 */
	@GetMapping("/showcomments/{id}")
	public ResponseEntity<List<CommentDto>> showAllComments(@PathVariable @RequestBody Long id){
		
		return new ResponseEntity<>(postService.showAllComments(id), HttpStatus.OK);
	}
	/*
	 * Get one comment by using comment id.
	 */
	@GetMapping("/showcomment/{id}")
	public ResponseEntity<CommentDto> showComment(@PathVariable @RequestBody Long id){
		
		return new ResponseEntity<>(postService.showComment(id), HttpStatus.OK);
	}
	/*
	 * Get post using post id.
	 */
	@GetMapping("/get/{id}")
	public ResponseEntity<PostDto> showSinglePost(@PathVariable @RequestBody Long id){
		
		return new ResponseEntity<>(postService.showSinglePost(id), HttpStatus.OK);
	}
}
