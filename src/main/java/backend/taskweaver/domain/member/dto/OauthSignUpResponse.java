package backend.taskweaver.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record OauthSignUpResponse(
        @Schema(description = "Oauth Profile에서 제공하는 회원의 이메일")
        String email
) {
}
