package study.data_jpa.repository.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.dto.MemberSearchCondition;
import study.data_jpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();

    // Query Dsl 이용한 검색 조건 동적쿼리
    Page<MemberDto> search(Pageable pageable, MemberSearchCondition memberSearchCondition);

}
