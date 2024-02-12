package backend.taskweaver.domain.task.dto;

import backend.taskweaver.domain.task.entity.enums.TaskStateName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


public class TaskResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class taskCreateResult {
        private Long id;
        private String title;
        private String content;
        private String startDate;
        private String endDate;
        private List<taskMemberResult> taskMember;
        private Integer taskStateName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class taskMemberResult {
        private Long id;
        private String imageUrl;
        private String nickname;
    }

}
