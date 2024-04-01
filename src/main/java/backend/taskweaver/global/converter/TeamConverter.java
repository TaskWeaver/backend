package backend.taskweaver.global.converter;

import backend.taskweaver.domain.team.dto.TeamInviteResponse;
import backend.taskweaver.domain.team.dto.TeamLeaderResponse;
import backend.taskweaver.domain.team.dto.TeamRequest;
import backend.taskweaver.domain.team.dto.TeamResponse;
import backend.taskweaver.domain.team.entity.Team;
import backend.taskweaver.domain.team.entity.TeamMember;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TeamConverter {

    public static Team toTeam(TeamRequest.teamCreateRequest request) {
        return Team.builder()
                .name(request.getName())
                .inviteLink(generateInviteLink())
                .build();
    }

    public static TeamResponse.teamCreateResult toCreateResponse(Team team) {
        return new TeamResponse.teamCreateResult(
            team.getId(),
            team.getName(),
            team.getInviteLink(),
            team.getTeamLeader(),
            team.getCreatedAt()
        );
    }

    public static TeamInviteResponse.InviteAnswerResult toInviteResponse(TeamMember teamMember) {
        return new TeamInviteResponse.InviteAnswerResult(
                teamMember.getId(),
                teamMember.getRole(),
                teamMember.getTeam().getId(),
                teamMember.getMember().getId()
        );
    }

    public static TeamResponse.AllTeamInfo toGetAllTeamResponse(Team team) {
        return new TeamResponse.AllTeamInfo(
                team.getId(),
                team.getName()
        );
    }
    public static TeamResponse.findTeamResult toGetTeamResponse(Team team, List<TeamMember> teamMembers) {
        List<TeamResponse.TeamMemberInfo> memberInfos = teamMembers.stream()
                .map(member -> new TeamResponse.TeamMemberInfo(
                        member.getMember().getId(),
                        member.getMember().getEmail(),
                        member.getMember().getImageUrl(),
                        member.getMember().getNickname(),
                        member.getRole().toString())) // Role 추가
                .collect(Collectors.toList());

        // 팀의 멤버 수 계산
        long memberCount = memberInfos.size();

        return new TeamResponse.findTeamResult(
                team.getId(),
                team.getName(),
                team.getTeamLeader(),
                team.getInviteLink(),
                team.getCreatedAt(),
                memberInfos,
                memberCount // 멤버 수 추가
        );
    }

    public static TeamLeaderResponse.ChangeLeaderResponse toChangeLeaderResponse(Team team, Long newLeaderId) {
        return TeamLeaderResponse.ChangeLeaderResponse.builder()
                .team_id(team.getId())
                .new_leader_id(newLeaderId)
                .build();
    }


    public static String generateInviteLink() {
        UUID uuid = UUID.randomUUID();
        // 도메인 결정 후
        return "https://localhost:" + "8081" + "/invite/" + uuid.toString();
    }

}
