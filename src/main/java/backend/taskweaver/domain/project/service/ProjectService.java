package backend.taskweaver.domain.project.service;

import backend.taskweaver.domain.project.dto.GetAllProjectResponse;
import backend.taskweaver.domain.project.dto.ProjectRequest;
import backend.taskweaver.domain.project.dto.ProjectResponse;
import backend.taskweaver.domain.project.dto.UpdateStateRequest;
import backend.taskweaver.domain.project.entity.Project;

import java.util.List;

public interface ProjectService{
    ProjectResponse createProject(ProjectRequest request, Long teamId);
    void createProjectMember(Project project, Long managerId);
    List<ProjectResponse> getAll(Long teamId);
    ProjectResponse getOne(Long projectId);
    void updateState(Long projectId, UpdateStateRequest request, Long memberId);
    void updateProject(Long projectId, ProjectRequest request, Long memberId);
    void delete(Long projectId, Long memberId);
}
