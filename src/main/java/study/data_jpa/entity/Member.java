package study.data_jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;


    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    // 프로젝트 추가 - 회원이 작성한 게시글 목록
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    // 프로젝트 추가 - 회원이 작성한 댓글 목록
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();



    public Member(String username){
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null){
            changeTeam(team);
        }

    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }


    // 프로젝트 - 추가 : post 연관관계 편의 메서드
    // 양방향 연관관계 세팅
    public Post writePost(Post post){
        posts.add(post);
        post.setMember(this);
        return post;
    }



    public Comment writeComment(Comment comment){
        comments.add(comment);
        comment.setMember(this);
        return comment;
    }



}
