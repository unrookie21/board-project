package study.data_jpa.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import study.data_jpa.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentDto {

    // 댓글 id
    private Long id;

    // 댓글 작성자
    private String username;

    // 댓글 내용
    private String content;

    // 댓글 작성 시간 추가
    private LocalDateTime createdDate;


    private Long parentId;

    //  대댓글
    private List<CommentDto> comments;

    public CommentDto(Comment comment){
        this.id = comment.getId();
        this.username = comment.getMember().getUsername();
        this.content = comment.getContent();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.comments = comment.getReplies().stream()
                .map(CommentDto :: new)
                .collect(Collectors.toList());
        // 댓글 등록 날짜 추가
        this.createdDate = comment.getCreatedDate();

    }

//    public CommentDto(Long id ,String username, String content, List<Comment> comments) {
//        this.id = id;
//        this.username = username;
//        this.content = content;
//        this.comments = comments.stream()
//                .map(c -> new CommentDto(c.getId(), c.getMember().getUsername(),
//                        c.getContent(), c.getReplies()))
//                .collect(Collectors.toList());
//
//
//    }


}
