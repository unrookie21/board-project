package study.data_jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.CommentDto;
import study.data_jpa.dto.CommentRequest;
import study.data_jpa.dto.ReplyRequest;
import study.data_jpa.entity.Comment;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.Post;
import study.data_jpa.repository.comment.CommentRepository;
import study.data_jpa.repository.member.MemberRepository;
import study.data_jpa.repository.post.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 댓글 작성
    @Transactional
    public CommentDto createComment(CommentRequest request) {

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("post not found"));

        Member member = memberRepository.findById(request.getMemberId()).
                orElseThrow(() -> new RuntimeException("member not found"));

        Comment comment = new Comment(request.getContent(), member, post);

        commentRepository.save(comment);

        return new CommentDto(comment);
    }

    // 대댓글 작성
    @Transactional
    public CommentDto createReply(Long parentCommentId,
                                  ReplyRequest request) {

        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("comment not found"));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("member not found"));

        Comment reply = new Comment(parentComment, null, member, request.getContent());

        parentComment.writeReply(member, reply);

        commentRepository.save(reply);

        return new CommentDto(reply);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long postId, Long commentId){
        Post findPost = postRepository.findPostWithCommentsById(postId)
                .orElseThrow(() -> new RuntimeException("post not found"));

        // 삭제하고자 하는 댓글
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(()-> new RuntimeException("comment not found"));

        List<Comment> comments = findPost.getComments();
        // 게시글의 댓글목록 가져온 상태

        // 부모 댓글인 경우, post 의 comment 컬렉션에서 부모 댓글 제거
        if (findComment.getParent() == null) {
            comments.remove(findComment); // db 와 객체간 정합성 일치를 위해서
        }

        // 대댓글인 경우, 부모 댓글의 replies 컬렉션에서 자식 댓글 제거
        if (findComment.getParent() != null){ // 대댓글인 경우
            Comment parentComment = commentRepository.findById(findComment.getParent().getId()).get();
            parentComment.getReplies().remove(findComment);

        }

        commentRepository.delete(findComment); // 실제로 db 에서 제거
    }

    // 댓글 수정
    @Transactional
    public CommentDto updateComment(Long commentId,
                                    CommentRequest request){
        // id 로 수정할 댓글 조회
        Comment foundComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("comment not found"));

        String toUpdateContent = request.getContent();

        // 변경 감지
        foundComment.changeContent(toUpdateContent);

        return new CommentDto(foundComment);

    }



}
