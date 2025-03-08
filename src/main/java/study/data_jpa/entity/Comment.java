package study.data_jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity{
    // 댓글 등록시간 표시위해서 BaseEntity 상속

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    // 댓글 내용
    private String content;

    // 생성자
    public Comment(String content) {
        this.content = content;
    }

    public Comment(String content, Member member, Post post) {
        this.content = content;
        this.post = post;
        post.getComments().add(this);
        member.writeComment(this);
        this.member = member;
    }

    public Comment(Comment parent, Post post, Member member, String content) {
        this.parent = parent;
//        this.post = post;
        this.member = member;
        this.content = content;
    }

    // 누가 작성한 댓글인지
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    // 어떤 게시글에 달린 댓글인지.
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // 댓글은 대댓글 여러개를 가질 수 있다.

    @OneToMany(mappedBy = "parent" , orphanRemoval = true)
    // 자식 엔티티들
    private List<Comment> replies = new ArrayList<>();

    // 자식이 있으니, 부모 엔티티도 있어야겠지
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    //대댓글 추가
    // 누가, 어떤 댓글을 작성할 것인지.
    public Comment writeReply(Member member, Comment comment){
        // 댓글에 대댓글 추가
        replies.add(comment);
        comment.setParent(this);

        // 회원의 댓글 목록 추가
        member.writeComment(comment);

        return comment;
    }


    // 댓글 내용 수정 메소드
    public void changeContent(String content) {
        this.content = content;
    }


}
