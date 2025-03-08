package study.data_jpa.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.entity.Member;
import study.data_jpa.repository.member.MemberRepository;
//import study.data_jpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)

public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
//    @Autowired TeamRepository teamRepository;
    @Autowired EntityManager em;
    // 위에 3개 다 같은 엔티티 매니저를 사용함 .


    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Optional<Member> byId = memberRepository.findById(savedMember.getId());
        // 있을 수도 있고 없을 수도 있기 때문에 Optional 로 제공됨.
        Member findMember = byId.get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());


    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member1.getId()).get();

        System.out.println(memberRepository.count());






    }

    @Test
    public void findByUsernameAndAgeGreaterThan(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);



    }

    @Test void findHello(){

        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);


        List<Member> result = memberRepository.findTop3HelloBy();
        System.out.println(result.get(1).getAge());


    }

    @Test
    public void testQuery(){

        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);


        List<Member> result = memberRepository.findUserByUsername("AAA", 10);
        assertThat(result.get(0)).isEqualTo(m1);


    }

    @Test
    public void findUsernameList(){
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList) {
            System.out.println("s = " + s);

        }


    }

//    @Test
//    public void findMemberDto(){
//
//        Team team = new Team("teamA");
//        teamRepository.save(team);
//
//        Member m1 = new Member("AAA", 10);
//        m1.setTeam(team);
//        memberRepository.save(m1);
//
//        List<MemberDto> usernameList = memberRepository.findMemberDto();
//        for (MemberDto memberDto : usernameList) {
//            System.out.println("dto = " + memberDto);
//
//        }
//
//    }

    @Test
    public void findByNames(){

        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));


        for (Member member : result) {
            System.out.println("member= " + member);
        }



    }

    @Test
    public void returnType(){

        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> findMember = memberRepository.findListByUsername("adfssss");
        // 얘는 findMember 에 null 반환됨.
        // 그냥 마음편하게 Optional 로 받는 것이 좋겠다...(조회했을때 결과가 있을수도, 없을수도 있는경우)

        List<Member> result = memberRepository.findListByUsername("asdfaf");
        // 여기서 result 에 null 이 반환된다고 생각할 수 있는데 아님. 빈 컬렉션 반환되어서
        // size 찍어보면 0 나옴



        Optional<Member> findMember2 =memberRepository.findOptionalMemberByUsername("AAA");

    }

    @Test
    public void paging(){
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // 0페이지에서 3개가져와.
        int age = 10;


        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        // ==>>> 반환 타입을 Page 로 받으면, total count 쿼리까지 같이 날라감 ㅋㅋ 사기네

        // 페이지를 DTO 로 쉽게 변환하는 방법

//        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));




        //then
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0); // 페이지 번호를 알 수 있음
        assertThat(page.getTotalPages()).isEqualTo(2); // 첫번째 페이지에 3개, 두번째 페이지에 2개
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();



    }

    @Test
    public void bulkUpdate(){

        //given

        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        //when
        int resultCount = memberRepository.bulkAgePlus(20); // 20살 이상인 애들은 나이 +1


        // 벌크 연산에서 조심해야 할 점
        // 벌크 연산에서는 영속성 컨텍스트를 무시하고 db 에 때려버림.
        // 영속성 컨텍스트는 모르는 상태..
        // 그렇기 때문에 벌크연산 한 후에 영속성 컨텍스트를 날려 버려야 한다.

        // 추가 사항.
//        em.flush(); // 변경되지 않은 사항 변경하고
//        em.clear(); // 영속성 컨텍스트 날려버림

//        List<Member> result = memberRepository.findByUsername("member5");
//        Member member5 = result.get(0);

//        System.out.println("member = " + member5);


//        assertThat(resultCount).isEqualTo(3);



    }

//    @Test
//    public void findMemberLazy(){
//        // given
//        // member1 - > teamA
//        // member2 - > teamB
//
//        Team teamA = new Team("teamA");
//        Team teamB = new Team("teamB");
//        teamRepository.save(teamA);
//        teamRepository.save(teamB);
//
//        Member member1 = new Member("member1", 10, teamA);
//        Member member2 = new Member("member2", 10, teamB);
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        em.flush();
//        em.clear();
//
////        List<Member> members = memberRepository.findAll();
//        List<Member> members = memberRepository.findMemberFetchJoin();
//
//
//
//        for (Member member : members) {
//            System.out.println("member = " + member.getUsername());
//            System.out.println("member.team = " + member.getTeam().getName());
//
//        }
//
//
//    }

    @Test
    public void queryHint(){
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        em.flush();
        em.clear();


//        Member findMember = memberRepository.findById(member1.getId()).get();
//        findMember.setUsername("member2");
        // 이렇게 변경을 하는 경우도 있지만, 완전히 조회용으로만 쓰는 경우도 있을 것임.
        // 이 경우에 최적화하는 방법이 있다.

        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");
        // readonly 상태이기 때문에 그냥 무시해버림.
        // 실제로 update 쿼리가 안 나가는 것을 확인 할 수 있음.
        // 근데 거의 사용하지는 않는다..

        em.flush();


    }

    @Test
    public void callCustom(){
        List<Member> result = memberRepository.findMemberCustom();

    }


//    @Test
//    public void queryByExample(){
//        // given
//        Team teamA = new Team("temA");
//        em.persist(teamA);
//
//        Member m1 = new Member("m1", 0, teamA);
//        Member m2 = new Member("m2", 0, teamA);
//        em.persist(m1);
//        em.persist(m2);
//
//        em.flush();
//        em.clear();
//
//        //when
//
//        Member member = new Member("m1"); // m1 이름을 찾고 싶을때 엔티티를 만든다.
//        Team team = new Team("teamA");
//        member.setTeam(team);
//
//
//
//        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("age");
//
//        Example<Member> example = Example.of(member, matcher);
//
//        List<Member> members = memberRepository.findAll(example);
//
//        // 신선하다고 생각할 수 있지만... inner join 은 되는데 outer join 은 안됨 .
//
//        assertThat(members.get(0).getUsername()).isEqualTo("m1");
//
//
//
//    }


//    @Test
//    public void projections(){
//
//        Team teamA = new Team("temA");
//        em.persist(teamA);
//
//        Member m1 = new Member("m1", 0, teamA);
//        Member m2 = new Member("m2", 0, teamA);
//        em.persist(m1);
//        em.persist(m2);
//
//        em.flush();
//        em.clear();
//
//        // when
////        List<UsernameOnly> result = memberRepository.findProjectionsByUsername("m1");
////        // usernameonly 라는 인터페이스 만들어놓으면, spring data jpa가 구현체를 만들어야겠구나해서 만들고,
////        // 필요한 필드를 projection 해온다.
//
////        for (UsernameOnly usernameOnly : result) {
////            System.out.println("usernameOnly = " + usernameOnly);
////
////        }
//
//
//        List<UsernameOnlyDto> result = memberRepository.findDtoProjectionsByUsername("m1");
//
//        for (UsernameOnlyDto  usernameOnly : result) {
//            System.out.println("usernameOnly = " + usernameOnly);
//
//        }
//
//
//
//    }
//
//    @Test
//    public void nativeQuery(){
//
//        Team teamA = new Team("temA");
//        em.persist(teamA);
//
//        Member m1 = new Member("m1", 0, teamA);
//        Member m2 = new Member("m2", 0, teamA);
//        em.persist(m1);
//        em.persist(m2);
//
//        em.flush();
//        em.clear();
//
//        // when
//        Member result = memberRepository.findByNativeQuery("m1");
//        System.out.println("result = " + result);
//
//
//    }




}