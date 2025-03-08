package study.data_jpa.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.PostDto;
import study.data_jpa.dto.PostRequestDto;
import study.data_jpa.dto.PostSearchCondition;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.Post;
import study.data_jpa.repository.member.MemberRepository;
import study.data_jpa.repository.post.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시글 목록 페이징 조회 (처음에 사용했던 방식. 이후 검색 조건 페이징으로 교체)
    // V1
    public Page<PostDto> findPostsPaging(Pageable pageable) {

        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(p -> PostDto.from(p));
    }

    // 검색 조건 페이징 추가
    // V2
    public Page<PostDto> findPostByCondition(Pageable pageable, PostSearchCondition condition){
        Page<PostDto> posts = postRepository.searchByTitleAuthorContent(pageable, condition);
        return posts;
    }

    // 클릭시 해당 게시글 조회
    public PostDto findPostClicked(Long postId) {

        Post findPost = postRepository.findPostById(postId);
        return PostDto.from(findPost);
    }

    // 게시글 등록
    @Transactional
    public PostDto createPost(PostRequestDto request) {

        Member member = memberRepository.findByUsername(request.getName());
        Post post = new Post(member, request.getTitle(), request.getContent());

        postRepository.save(post);

        return PostDto.from(post);
    }

    // 게시글 내용 수정
    @Transactional
    public PostDto editPost(Long postId, PostRequestDto request) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));

        String toUpdateContent = request.getContent();

        post.updateContent(toUpdateContent); // 변경감지 자동으로 해줌

        return PostDto.from(post);
    }

    // 게시글 삭제
    @Transactional
    public void removePost(Long postId) {

        Post findPost = postRepository.findPostWithCommentsById(postId)
                .orElseThrow(()-> new RuntimeException("post not found"));

        // findPost 에는 comments 까지 join 해서 가져온 상태.

        postRepository.delete(findPost);
    }



}
