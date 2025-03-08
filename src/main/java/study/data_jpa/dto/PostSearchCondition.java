package study.data_jpa.dto;

import lombok.Data;

@Data
public class PostSearchCondition {

    // 제목
    private String title;
    // 작성자
    private String author;
    // 본문
    private String content;

}
