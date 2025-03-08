package study.data_jpa.controller;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.entity.Comment;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.Post;
import study.data_jpa.repository.post.PostRepository;


@SpringBootTest
@Transactional
class CommentControllerTest {

    @Autowired
    EntityManager em;

    @Autowired CommentController commentController;
    @Autowired
    PostRepository postRepository;

    @Test
    @Commit
    public void 댓글삭제(){
        // 회원 등록
        // 게시글 등록
        // 게시글에 댓글 하나 등록


        Member member = new Member("member1");


        Post post = new Post(member, "제목1", "내용1");


        Comment comment = new Comment("댓글1", member, post);

        // 대댓글 만들기 부모, 작성자, content
        Comment reply = new Comment(comment, null, member, "대댓글");
        comment.getReplies().add(reply);
        member.writeComment(comment);

        em.persist(member);
        em.persist(post);
        em.persist(comment);
        em.persist(reply);

        em.flush();
        em.clear();
        // 영속성 컨텍스트 내용 DB 에 반영

////         댓글 삭제
//        commentController.deleteComment(post.getId(), comment.getId());
////         댓글 삭제 완료

//         대댓글 삭제
        commentController.deleteComment(post.getId(), reply.getId());

        Post findPost = postRepository.findPostById(post.getId());

        Assertions.assertThat(findPost.getComments().get(0).getReplies().size()).isEqualTo(0);





    }

}