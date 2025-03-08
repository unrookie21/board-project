package study.data_jpa.controller;

import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.PostDto;
import study.data_jpa.dto.PostRequestDto;
import study.data_jpa.dto.PostSearchCondition;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.Post;
import study.data_jpa.repository.member.MemberRepository;
import study.data_jpa.repository.post.PostRepository;
import study.data_jpa.service.PostService;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    // 게시글 목록 조회
    // 페이징 조회
    // /posts http 요청오면 페이징 처리해서 result 반환
//    @GetMapping("/posts")
//    public Page<PostDto> findAllPages(@PageableDefault(size = 15) Pageable pageable){
//
//        return postService.findPostsPaging(pageable);
//
//        //        Page<Post> posts = postRepository.findAll(pageable);
////        Page<Post> posts = postRepository.findAll(pageable);
//
////        Page<PostDto> result = posts.map(p -> new PostDto(p.getId(), p.getTitle(), p.getMember().getUsername()));
//
////        Page<PostDto> result = posts.map(p -> PostDto.builder()
////                .id(p.getId())
////                .title(p.getTitle())
////                .name(p.getMember().getUsername())
////                .build()
////        );
//
////        return posts.map(p -> PostDto.from(p));
//    }

    @GetMapping("/posts")
    public Page<PostDto> findPostsByCondition(Pageable pageable, PostSearchCondition condition){
        return postService.findPostByCondition(pageable, condition);
    }

    // 클릭시 게시글 조회
    @GetMapping("/posts/{id}")
    public PostDto showClickedPostContent(@PathVariable("id") Long id){
        return postService.findPostClicked(id);
        //        Post findPost = postRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("해당 ID 의 게시글 존재하지 않습니다."));
//        Post findPost = postRepository.findPostById(id);

//        return PostDto.from(findPost);

//        PostDto build = PostDto.builder()
//                .title(findPost.getTitle())
//                .name(findPost.getMember().getUsername())
//                .comments(findPost.getComments())
//                .content(findPost.getContent())
//                .createdDate(findPost.getCreatedDate())
//                .build();
//
//        return build;

//        return new PostDto(findPost.getTitle(), findPost.getMember().getUsername(),
//                findPost.getComments() ,findPost.getContent(), findPost.getCreatedDate());
    }

    // 게시글 등록
    @PostMapping("/posts/new")
    public PostDto writePost(@RequestBody PostRequestDto request){

        return postService.createPost(request);

//        Member member = memberRepository.findByUsername(request.getName());
//        Post post = new Post(member, request.getTitle(), request.getContent());
//
//        postRepository.save(post);

        // 제목, 이름 , 댓글 , 내용
//        PostDto postDto = new PostDto(post.getTitle(), post.getMember().getUsername(),
//                post.getComments(), post.getContent(), post.getCreatedDate());

//        PostDto build = PostDto.builder()
//                .title(post.getTitle())
//                .name(post.getMember().getUsername())
//                .comments(post.getComments())
//                .content(post.getContent())
//                .createdDate(post.getCreatedDate())
//                .build();
//        return build;

//        return postDto;


//        return PostDto.from(post);
    }

    // 게시글 내용 수정
    @PatchMapping("/posts/{id}")
    @Transactional
    public PostDto postEdit(@PathVariable("id") Long id, @RequestBody PostRequestDto request){

        return postService.editPost(id, request);

//        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));
//
//        String toUpdateContent = request.getContent();
//
//        post.updateContent(toUpdateContent); // 변경감지 자동으로 해줌

//        PostDto postDto = new PostDto(post.getTitle(), post.getMember().getUsername(),
//                post.getComments(), post.getContent(), post.getCreatedDate());

//        PostDto build = PostDto.builder()
//                .title(post.getTitle())
//                .name(post.getMember().getUsername())
//                .comments(post.getComments())
//                .content(post.getContent())
//                .createdDate(post.getCreatedDate())
//                .build();
//
//
//        return build;
//        return postDto;

//        return PostDto.from(post);

    }

    // 게시글 삭제
    @PostMapping("posts/{id}")
    public void deletePost(@PathVariable("id") Long id){

        postService.removePost(id);

//        Post findPost = postRepository.findPostWithCommentsById(id)
//                .orElseThrow(()-> new RuntimeException("post not found"));
//
//        // findPost 에는 comments 까지 join 해서 가져온 상태.
//
//        postRepository.delete(findPost);

    }

//    // 게시글 좋아요
//    @PostMapping("posts/{id}/likes")
//    public void plusLikes(@PathVariable("id") Long id){
//
//        Post findPost = postRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("post not found"));
//
//        findPost.plusLikeCount(); // 좋아요 개수 1개 증가
//    }









}
