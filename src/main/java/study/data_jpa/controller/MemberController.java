package study.data_jpa.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.dto.MemberRequest;
import study.data_jpa.dto.MemberSearchCondition;
import study.data_jpa.entity.Member;

import study.data_jpa.repository.member.MemberRepository;
import study.data_jpa.service.MemberService;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

//    // 회원 목록 조회 ====> 검색 기능 추가
//    @GetMapping("/members")
//    // PageRequest 객체 만들어서 pageable 에 주입을 해줌.
//    public Page<MemberDto> list(Pageable pageable){
//        Page<Member> page = memberRepository.findAll(pageable);
//
//        Page<MemberDto> map = page.map(m -> new MemberDto(m.getUsername(), m.getAge()));
//        return map;
//    }

    // 위 코드에서 update 된 버전 ( 이름 또는 나이로 조건 검색)
    @GetMapping("/members")
    public Page<MemberDto> showMembersByCondition(Pageable pageable,
                                                  MemberSearchCondition memberSearchCondition){
         return memberService.findMembersByCondition(pageable, memberSearchCondition);
//          return memberRepository.search(pageable, memberSearchCondition);
    }

    // 회원 가입기능
    @PostMapping("/members/new")
    public void joinMember(@RequestBody MemberRequest request){
        memberService.join(request);
//        Member member = new Member(request.getUsername(), request.getAge());
//        memberRepository.save(member);
    }

    // 벌크성 수정 연산(회원의 나이 증가)
    @PostMapping("/members/addAge")
    public void addAge(@RequestParam("age") int age){
        memberService.addAgeGreaterThan(age);
//        memberRepository.bulkAgePlus(age); // 매개변수로 입력받은 age 보다 크거나 같은
        // member 의 age 를 1증가 시킴.
    }








}
