package lt.rokas.blog.domain;
/*
 * Post objects repository
 * Used to store Post entities
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.rokas.blog.model.Post;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
