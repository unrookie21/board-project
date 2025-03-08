package study.data_jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시글 엔티티
 * 제목, 내용, 댓글
 */

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity{
    // 게시글 작성시간 표시 위해서 BaseEntity 상속

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    // 좋아요
    private int likes;


    public Post(Member member, String title, String content) {
        member.writePost(this);
        this.title = title;
        this.content = content;
    }

    // 연관관계의 주인 member
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    // 하나의 게시글에 여러개의 댓글이 있을 수 있음. OneToMany
    @OneToMany(mappedBy = "post" , orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // post 와 comment 사이의 연관관계 편의 메서드
    // 게시글에 댓글 추가 (어떤 회원이 어떤 댓글을 작성했는지 알아야함)
    public Comment addComment(Member member, Comment comment){

        // 게시글에 댓글 추가
        comments.add(comment);
        comment.setPost(this);

        // 회원이 작성한 댓글 목록에 댓글 추가
        member.writeComment(comment);

        return comment;
    }

    // 게시글 내용 수정

    public void updateContent(String content){
        this.content = content;
    }

    // 좋아요 개수 추가
    public void plusLikeCount(){
        this.likes++;
    }








}
