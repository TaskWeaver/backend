package backend.taskweaver.domain.team.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;


public class TeamResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class teamCreateResult {
        Long id;
        String name;
        String inviteLink;
        Long teamLeader;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findTeamResult {
        @Schema(description = "팀 id", example = "1")
        Long id;

        @Schema(description = "팀 이름", example = "팀 이름")
        String name;

        @Schema(description = "팀 리더 id", example = "1")
        Long teamLeader;

        @Schema(description = "초대 링크", example = "https://localhost:8081/invite/abea91ea-c861-40aa-b1fe-be2fe98ba4e2")
        String inviteLink;

        @Schema(description = "생성 날짜", example = "2024-02-08T22:58:10.061223")
        LocalDateTime createdAt;
    }
}
