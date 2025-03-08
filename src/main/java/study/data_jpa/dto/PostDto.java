package study.data_jpa.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import study.data_jpa.entity.Comment;
import study.data_jpa.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostDto {

    // 게시글 id
    private Long id;
    // 제목
    private String title;
    // 작성자

    private String name;

    // 댓글 목록
    private List<CommentDto> comments = new ArrayList<>();

    // 본문 내용
    private String content;

    // 게시글 작성 시간 추가
    private LocalDateTime createdDate;

    // 게시글 좋아요 개수
    private int likes;


//    public PostDto(Long id, String title, String name) {
//        this.id = id;
//        this.title = title;
//        this.name = name;
//    }
//
//    public PostDto(String title, String name, List<Comment> comments, String content, LocalDateTime createdDate) {
//        this.title = title;
//        this.name = name;
//        this.comments =
////                comments.stream().map(c -> new CommentDto(c.getId(),c.getMember().getUsername(),
////                        c.getContent(), c.getReplies()))
////                        .collect(Collectors.toList());
//
//                comments.stream().map(c-> new CommentDto(c)).collect(Collectors.toList());
//
//
//        this.content = content;
//        // 게시글 날짜 추가
//        this.createdDate = createdDate;
//    }

    @Builder
    public PostDto(Long id, String title, String name, List<Comment> comments,
                   String content, LocalDateTime createdDate, int likes){
        this.id = id;
        this.title = title;
        this.name = name;
        this.comments = comments != null ? comments.stream()
                .map(c-> new CommentDto(c)).collect(Collectors.toList()) : null;
        this.content = content;
        this.createdDate = createdDate;
        this.likes = likes;
    }

    public static PostDto from(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .name(post.getMember().getUsername())
                .comments(post.getComments())
                .content(post.getContent())
                .createdDate(post.getCreatedDate())
                .likes(post.getLikes())
                .build();
    }
}
