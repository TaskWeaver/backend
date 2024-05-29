package backend.taskweaver.domain.notification.controller;

import backend.taskweaver.domain.notification.service.NotificationService;
import backend.taskweaver.global.code.ApiResponse;
import backend.taskweaver.global.code.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1")
@Tag(name = "알림")
@RequiredArgsConstructor
@Slf4j
@RestController
@EnableJpaAuditing
public class NotificationController {

    private final NotificationService notificationService;
    @Operation(summary =  "로그인한 유저의 알림 리스트 전체 조회")
    @GetMapping("/notification")
    public ResponseEntity<ApiResponse> AllNotification(@AuthenticationPrincipal User user) {
        ApiResponse apiResponse = ApiResponse.builder()
                .result(notificationService.getAllNotificationsForUser(Long.parseLong(user.getUsername())))
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
