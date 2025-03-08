package study.data_jpa.repository.member;

import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.dto.MemberSearchCondition;
import study.data_jpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>,
        MemberRepositoryCustom {
    // 스프링 데이터 jpa 가 애플리케이션 로딩 시점에 구현체를 만들어서 주입해줌 (프록시로)
    // @Repository 생략 가능함.

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy();

    List<Member> findTop3HelloBy();

//    List<Member> findByUsername(String username);

    Member findByUsername(String username);




    // jpql 을 직접 작성할 수 있다. ==> 실무에서 많이 사용한다.
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUserByUsername(@Param("username") String username, @Param("age") int age);

    // Member 의 Username 가져오기.
    @Query("select m.username from Member m")
    List<String> findUsernameList();


    // DTO 로 조회하기!
//    @Query("select new study.data_jpa.dto.MemberDto(m.id, m.username, t.name ) from Member m join m.team t")
//    List<MemberDto> findMemberDto();


    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

//-------------------------
    // 반환 타입

    List<Member> findListByUsername(String username); // 컬렉션
    Member findMemberByUsername(String username); // 단건
    Optional<Member> findOptionalMemberByUsername(String username);// 단건 optional

    Page<Member> findByAge(int age, Pageable pageable);


    // 벌크성 수정연산
    @Modifying(clearAutomatically = true) // modifying 애노테이션이 있어야 executeUpdate 를 실행함
    // 그렇지 않으면, getResultList 랑 getSingleResult 를 호출함.
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

//
//    @Query("select m from Member m join fetch m.team")
//    List<Member> findMemberFetchJoin();
    // n + 1 문제를 해결하기 위해서 fetch join 을 썼음.
    // 근데 fetch join 을 쓰려면 이렇게 쿼리를 계속 작성하는 것이 귀찮음
    // 이것을 @EntityGraph 가 도와준다.

//
//    @Override
//    @EntityGraph(attributePaths = {"team"}) // JPQL 을 짜기 싫어. entity graph 는 그냥
//    // fetch join 이라 생각하면 됨. fetch join 을 편하게 사용할 수 있다.
//    List<Member> findAll();
//
//
//    @EntityGraph(attributePaths = ("team"))
//    List<Member> findEntityGraphByUsername(@Param("username") String username);



    // jpa hint

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);







//    // 프로젝션 사용법
//    List<UsernameOnly> findProjectionsByUsername(String username);
//
//    List<UsernameOnlyDto> findDtoProjectionsByUsername(String username);



//    // 네이티브 쿼리
//    @Query(value = "select * from member where username = ?", nativeQuery = true)
//    Member findByNativeQuery(String username);







}
