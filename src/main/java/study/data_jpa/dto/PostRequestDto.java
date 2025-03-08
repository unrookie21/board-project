package study.data_jpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto{

    @NotBlank(message = "글 제목을 입력해주세요")
    private String title;
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "본문을 입력해주세요")
    private String content;

}