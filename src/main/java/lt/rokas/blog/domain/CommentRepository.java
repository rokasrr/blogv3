package lt.rokas.blog.domain;
/*
 * Comment objects repository
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.rokas.blog.model.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
