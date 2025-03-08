package study.data_jpa.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.data_jpa.dto.PostDto;
import study.data_jpa.dto.PostSearchCondition;

public interface PostRepositoryCustom {

    //  QueryDSL 검색 조건 쿼리(제목, 작성자, 본문 내용 기준 검색)

    Page<PostDto> searchByTitleAuthorContent(Pageable pageable, PostSearchCondition condition);


}
