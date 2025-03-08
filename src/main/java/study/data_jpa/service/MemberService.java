package study.data_jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.dto.MemberRequest;
import study.data_jpa.dto.MemberSearchCondition;
import study.data_jpa.entity.Member;
import study.data_jpa.repository.member.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 목록 조회
    public Page<MemberDto> findMembersByCondition(Pageable pageable,
                                                  MemberSearchCondition memberSearchCondition){
        return memberRepository.search(pageable, memberSearchCondition);
    }


    // 회원 가입
    @Transactional
    public void join(MemberRequest request){
        Member member = new Member(request.getUsername(), request.getAge());
        memberRepository.save(member);
    }


    // 벌크성 수정 연산(회원 나이 증가)
    @Transactional
    public void addAgeGreaterThan(int age) {
        memberRepository.bulkAgePlus(age);
    }



}
