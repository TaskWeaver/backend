package backend.taskweaver.domain.notification.service;

import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.domain.notification.dto.NotificationResponse;
import backend.taskweaver.domain.notification.entity.Notification;
import backend.taskweaver.domain.notification.entity.NotificationMember;
import backend.taskweaver.domain.notification.entity.enums.isRead;
import backend.taskweaver.domain.notification.repository.NotificationMemberRepository;
import backend.taskweaver.domain.notification.repository.NotificationRepository;
import backend.taskweaver.global.converter.NotificationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationMemberRepository notificationMemberRepository;
    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository; // MemberRepository 추가
    public List<NotificationResponse.AllNotificationInfo> getAllNotificationsForUser(Long memberId) {
        // 로그인한 사용자의 모든 알림 조회
        List<NotificationMember> notificationMembers = notificationMemberRepository.findByMemberId(memberId);

        // Member의 isRead 값을 'YES'로 업데이트
        memberRepository.findById(memberId).ifPresent(member -> {
            member.setIsRead(isRead.YES); // setIsRead 메소드를 Member 엔티티에 추가해야 함
            memberRepository.save(member);
        });

        // 조회된 알림을 NotificationResponse.AllNotificationInfo 객체로 변환
        return notificationMembers.stream()
                .map(notificationMember -> NotificationConverter.toGetAllNotificationResponse(notificationMember.getNotification(), notificationMember))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteOldNotifications(LocalDateTime cutoffDate) {
        List<Notification> oldNotifications = notificationRepository.findByCreatedAtBefore(cutoffDate);
        notificationRepository.deleteAll(oldNotifications);
    }
}
