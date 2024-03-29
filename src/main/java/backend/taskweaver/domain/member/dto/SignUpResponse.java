package backend.taskweaver.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignUpResponse(
        @Schema(description = "회원 아이디", example = "1")
        Long id,
        @Schema(description = "회원 이메일", example = "xxx@naver.com")
        String email,
        @Schema(description = "회원 닉네임", example = "코난")
        String nickname
) {

}