package backend.taskweaver.domain.member.entity;

import backend.taskweaver.domain.BaseEntity;
import backend.taskweaver.domain.member.entity.enums.LoginType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    // unique = true를 추가하여, 동일한 이메일을 가지고 가입하려고 할 때 DataIntegrityViolationException이 일어나게 한다.
    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String imageUrl;

//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private isRead isRead;

    public void updatePassword(String password) {
        this.password = password;
    }
}
