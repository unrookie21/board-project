package study.data_jpa.repository.post;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data_jpa.entity.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> , PostRepositoryCustom {

    @Override
    @EntityGraph(attributePaths = {"member"})
    Page<Post> findAll(Pageable pageable);


    @Query("select p from Post p join fetch p.member where p.id = :id")
    Post findPostById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"comments"})
    // entitygraph 로 조회하면, fetch join 을 하면서 페이징도 가능
    Optional<Post> findPostWithCommentsById(Long id);
    // post 삭제 -> 연관된 comments 삭제 -> comment 와 연관된 replies 삭제













}
