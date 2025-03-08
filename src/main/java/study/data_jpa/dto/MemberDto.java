package study.data_jpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// querydsl 에서 projection 할때, MemberDto 클래스를 생성해줘야하기 때문에 기본 생성자 필요함
public class MemberDto {

    private String username;
    private int age;


    public MemberDto(String username, int age) {
        this.username = username;
        this.age=  age;


    }
}
