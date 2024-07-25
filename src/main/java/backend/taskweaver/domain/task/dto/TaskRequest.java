package backend.taskweaver.domain.task.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class TaskRequest {

    @Getter
    public static class taskCreate {
        @Schema(description = "태스크 제목", example = "Task Title")
        String title;

        @Schema(description = "태스크 내용", example = "Task Content")
        String content;

        @Schema(description = "태스크 시작 날짜", example = "2024-01-01")
        String startDate;

        @Schema(description = "태스크 종료 날짜", example = "2024-12-31")
        String endDate;

        @Schema(description = "태스크 참여자", example = "[1, 2, 3]")
        List<Long> members;

        @Schema(description = "이모지 ID", example = "1")
        Long emojiId;

        @Schema(description = "부모 태스크 ID", example = "1")
        Long parentTaskId;

    }

    @Getter
    public static class taskStateChange {
        @Schema(description = "변경하고자 하는 태스크 상태", example = "0, 1, 2")
        Integer taskState;
    }

    @Getter
    public static class taskChange {
        @Schema(description = "변경하고자 하는 태스크 제목", example = "Task Title")
        String taskTitle;

        @Schema(description = "변경하고자 하는 태스크 내용", example = "Task Content")
        String taskContent;

        @Schema(description = "변경하고자 하는 마감 기한", example = "2024-12-31")
        String endDate;

        @Schema(description = "변경하고자 하는 태스크 참여자", example = "[1, 2, 3]")
        List<Long> members;

        @Schema(description = "삭제하고자 하는 태스크", example = "[1, 2, 3]")
        List<Long> deleteFiles;

        @Schema(description = "변경하고자 하는 이모지", example = "1")
        Long emojiId;

    }

    @Getter
    public static class todoList {

        @Schema(description = "마감 날짜", example = "2024-12-31")
        String endDate;

    }
}
