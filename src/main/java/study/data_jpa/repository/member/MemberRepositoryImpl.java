package study.data_jpa.repository.member;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.dto.MemberSearchCondition;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.QMember;

import java.util.List;

import static study.data_jpa.entity.QMember.*;


public class  MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Member> findMemberCustom() {

        return em.createQuery("select m from Member m", Member.class)
                .getResultList();

    }

    // 쿼리 dsl 관련
    @Override
    public Page<MemberDto> search(Pageable pageable, MemberSearchCondition memberSearchCondition) {

        List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .where(usernameEq(memberSearchCondition.getUsername()),
                        ageEq(memberSearchCondition.getAge()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // count 쿼리
        Long total = queryFactory
                .select(member.count())
                .from(member)
                .where(usernameEq(memberSearchCondition.getUsername()),
                        ageEq(memberSearchCondition.getAge()))
                .fetchOne();

        return new PageImpl<>(result, pageable, total);


    }



    private BooleanExpression usernameEq(String usernameCond) {
        return StringUtils.hasText(usernameCond) ? member.username.eq(usernameCond) : null;
    }

    private BooleanExpression ageEq(Integer ageCond){
        return ageCond != null ? member.age.eq(ageCond) : null;
    }


}
