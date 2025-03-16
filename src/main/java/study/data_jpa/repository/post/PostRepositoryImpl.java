package study.data_jpa.repository.post;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import study.data_jpa.dto.PostDto;
import study.data_jpa.dto.PostSearchCondition;
import study.data_jpa.entity.QMember;
import study.data_jpa.entity.QPost;

import java.util.List;

import static study.data_jpa.entity.QMember.*;
import static study.data_jpa.entity.QPost.*;

public class PostRepositoryImpl implements PostRepositoryCustom{

    // 커밋 테스트

    // EntityManger 랑 JpaQueryFactory 필요
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    // 생성자 주입
    public PostRepositoryImpl(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    // querydsl 사용
    @Override
    public Page<PostDto> searchByTitleAuthorContent(Pageable pageable, PostSearchCondition condition) {

        // 시행착오 : 필드 프로젝션의 경우에는 프로퍼티 이름이 같아야되는데,
        // PostDto 의 username 필드는 name 으로 되어있었기 때문에 as "name" 을 붙여 줬어야함.
        // 근데 안 붙여서, 작성자가 화면에 표시되지 않는 문제가 있었음.

        // 또한, 페이징은 되지만, 게시글을 클릭하고 나서는 어떤 기능도 동작하지 않았는데,
        // 이는 경로변수로 전달받은 postId 를 기반으로 엔티티에 대한 작업이 이루어져야하는데,
        // 필드 프로젝션을 할때 post.id 를 안 적어서 postId 를 전달받지 못하고 있었음.

        // 페이징 쿼리
        List<PostDto> result = queryFactory
                .select(Projections.fields(PostDto.class,
                        post.id,
                        post.title,
                        member.username.as("name")))
                .from(post)
                .leftJoin(post.member, member)
                .where(titleEq(condition.getTitle()),
                        authorEq(condition.getAuthor()),
                        contentEq(condition.getContent()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        // count 쿼리 따로 만들어줘야함
        Long count = queryFactory
                .select(post.count())
                .from(post)
                .leftJoin(post.member, member)
                .where(titleEq(condition.getTitle()),
                        authorEq(condition.getAuthor()),
                        contentEq(condition.getContent()))
                .fetchOne();


        return new PageImpl<>(result, pageable, count);
    }


    private BooleanExpression contentEq(String content) {
        return StringUtils.hasText(content) ? post.content.contains(content) : null;
    }

    private BooleanExpression authorEq(String author) {
        return StringUtils.hasText(author) ? post.member.username.eq(author) : null;
    }

    private BooleanExpression titleEq(String title) {
        return StringUtils.hasText(title) ? post.title.eq(title) : null;
    }


}
