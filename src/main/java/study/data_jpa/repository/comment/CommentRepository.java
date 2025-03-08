package study.data_jpa.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data_jpa.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {


}
