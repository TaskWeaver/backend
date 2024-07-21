package backend.taskweaver.domain.member.service;

import backend.taskweaver.domain.files.service.S3Service;
import backend.taskweaver.domain.member.dto.SignInRequest;
import backend.taskweaver.domain.member.dto.SignInResponse;
import backend.taskweaver.domain.member.dto.SignUpRequest;
import backend.taskweaver.domain.member.dto.SignUpResponse;
import backend.taskweaver.domain.member.entity.Member;
import backend.taskweaver.domain.member.entity.MemberRefreshToken;
import backend.taskweaver.domain.member.repository.MemberRefreshTokenRepository;
import backend.taskweaver.domain.member.repository.MemberRepository;
import backend.taskweaver.global.code.ErrorCode;
import backend.taskweaver.global.converter.MemberConverter;
import backend.taskweaver.global.exception.handler.BusinessExceptionHandler;
import backend.taskweaver.global.security.TokenProvider;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

import okio.FileMetadata;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class SignService {
    private final MemberRepository memberRepository;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;
//    private final RedisService redisService;
        private final S3Service s3Service;

    // 회원가입
    @Transactional
    public SignUpResponse registerMember(SignUpRequest request, MultipartFile profileImage) {
        String imageUrl;

        try {
            // 이미지 파일이 비어 있는지 확인
            if (profileImage == null || profileImage.isEmpty()) {
                // 디폴트 이미지 URL을 가져오는 방법
                imageUrl = s3Service.saveDefaultProfileImage(); // 디폴트 이미지를 저장하는 메소드 호출
            } else {
                // 업로드된 이미지 URL 가져오기
                imageUrl = s3Service.saveProfileImage(profileImage);
            }

            // 회원 정보 저장
            Member member = MemberConverter.toMember(request, encoder, imageUrl);
            member = memberRepository.saveAndFlush(member);

            return MemberConverter.toSignUpResponse(member);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessExceptionHandler(ErrorCode.DUPLICATED_EMAIL);
        } catch (IOException e) {
            throw new BusinessExceptionHandler(ErrorCode.PROFILE_IMAGE_UPLOAD_FAILED);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }





    //@Transactional(readOnly = true)
    @Transactional
    public SignInResponse signIn(SignInRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .filter(it -> encoder.matches(request.password(), it.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
        String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", member.getId(), member.getLoginType()));	// token -> accessToken
        String refreshToken = tokenProvider.createRefreshToken();	// 리프레시 토큰 생성
        // 리프레시 토큰이 이미 있으면 토큰을 갱신하고 없으면 토큰을 추가
        memberRefreshTokenRepository.findById(member.getId())
                .ifPresentOrElse(
                        it -> it.updateRefreshToken(refreshToken),
                        () -> memberRefreshTokenRepository.save(new MemberRefreshToken(member, refreshToken))
                );
       // redisService.setValues(request.email(), request.deviceToken());
        return MemberConverter.toSignInResponse(member, accessToken, refreshToken);
    }

    public void logout(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));
       // redisService.deleteValues(member.getEmail());
    }
}