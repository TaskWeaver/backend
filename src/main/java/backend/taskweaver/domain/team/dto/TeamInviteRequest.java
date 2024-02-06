package backend.taskweaver.domain.team.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TeamInviteRequest {
    @Getter
    public static class EmailInviteRequest {
        @Schema(description = "초대할 유저 이메일", example = "xxx@naver.com")
        String email;

        @Schema(description = "해당 팀 id", example = "1")
        Long team_id;
    }
}
