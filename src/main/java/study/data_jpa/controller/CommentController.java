package study.data_jpa.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.CommentDto;
import study.data_jpa.dto.CommentRequest;
import study.data_jpa.dto.ReplyRequest;
import study.data_jpa.entity.Comment;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.Post;
import study.data_jpa.repository.comment.CommentRepository;
import study.data_jpa.repository.member.MemberRepository;
import study.data_jpa.repository.post.PostRepository;
import study.data_jpa.service.CommentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/comments/new")
    public CommentDto createComment(@RequestBody CommentRequest request){
        return commentService.createComment(request);
    }

    // 대댓글작성
    @PostMapping("/{parentCommentId}/reply")
    public CommentDto createReply(@PathVariable("parentCommentId")Long parentCommentId,
                                  @RequestBody ReplyRequest request){
       return commentService.createReply(parentCommentId, request);
    }


    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable("postId") Long postId,
                              @PathVariable("commentId") Long commentId){

        commentService.deleteComment(postId, commentId);
    }


    // 댓글 수정기능 추가
    @PatchMapping("/comments/{commentId}/edit")
    public CommentDto updateComment(@PathVariable("commentId") Long commentId,
                              @RequestBody CommentRequest request) {
        return commentService.updateComment(commentId, request);
    }


}
