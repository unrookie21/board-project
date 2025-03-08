package study.data_jpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.data_jpa.repository.member.MemberRepository;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @Autowired EntityManager em;
    @Autowired MemberRepository memberRepository;

//    @Test
//    public void testEntity(){
//
//        //give
//        Team teamA = new Team("teamA");
//        Team teamB = new Team("teamB");
//        em.persist(teamA);
//        em.persist(teamB);
//
//        Member member1 = new Member("member1", 10, teamA);
//        Member member2 = new Member("member1", 10, teamA);
//        Member member3 = new Member("member1", 10, teamA);
//        Member member4 = new Member("member1", 10, teamA);
//
//        em.persist(member1);
//        em.persist(member2);
//        em.persist(member3);
//        em.persist(member4);
//
////        em.flush(); // 강제로 db 에 inset 날림
////        em.clear(); // 영속성 컨텍스트 다 비움
//
//
//        // 확인
//        List<Member> members = em.createQuery("select m from Member m", Member.class)
//                .getResultList();
//
//        for (Member member : members) {
//            System.out.println("member = " + member);
//            System.out.println("-> member.team =" + member.getTeam());
//
//        }
//
//    }

//    @Test
//    public void JpaEventBaseEntity() throws Exception{
//        //given
//        Member member = new Member("member1");
//        memberRepository.save(member); // 이때 @PrePersist 실행
//
//        Thread.sleep(100);
//        member.setUsername("member2");
//
//        em.flush(); // @PreUpdate 실행
//        em.clear();
//
//
//        // when
//
//        Member findMember = memberRepository.findById(member.getId()).get();
//
//        System.out.println("member = " + findMember.getCreatedDate());
//        System.out.println("member = " + findMember.getLastModifiedDate());
//
//
//        //then
//
//        System.out.println();
//    }




}