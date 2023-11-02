package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImp implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final UserService userService;
    private final TaskService taskService;

    public ProjectServiceImp(ProjectRepository projectRepository, ProjectMapper projectMapper, UserMapper userMapper, UserService userService, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        return projectMapper.convertToDto(projectRepository.findByProjectCode(code));
    }

    @Override
    public List<ProjectDTO> listAllProjects() {

        return projectRepository.findAll(Sort.by("projectCode")).stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {

        //Here at ui part we don't have a status part at the create form. Because of that we set it directly here as opened.
        dto.setProjectStatus(Status.OPEN);

        Project project = projectMapper.convertToEntity(dto);
        projectRepository.save(project);

    }

    @Override
    public void update(ProjectDTO dto) {

        //We convert the dto to Entity by the ui part informations.
        Project project = projectMapper.convertToEntity(dto);

        //We take the original entity from db and add its Id to our updated Project. Because ui part our project doesn't have id information.
        project.setId(projectRepository.findByProjectCode(dto.getProjectCode()).getId());

        //As well we take the project status from db part and add to our updated project.
        project.setProjectStatus(projectRepository.findByProjectCode(dto.getProjectCode()).getProjectStatus());

        //At the end we save the updated project to db.
        projectRepository.save(project);

    }

    @Override
    public void delete(String code) {

        Project project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);

        //We add this part to have the ability to use the same code after a project deleted. Normally when we delete project we only change its isDeleted field and it still at the db. Because of that we can not use its code. But by this line we change the deleted project's code.
        project.setProjectCode(project.getProjectCode() + "-" + project.getId());  // SP03-4

        projectRepository.save(project);

        //We add this line for the tasks of the project. When we delete a project tha task that belongs to that project must delete too. By this code we find the tasks that belongs to project and delete them too.
        taskService.deleteByProject(projectMapper.convertToDto(project));


    }

    @Override
    public void complete(String projectCode) {

        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

        taskService.completeByProject(projectMapper.convertToDto(project));


    }


    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        //This line will be handled by security in normal but we do not have security yet. Because of that we write this line of hard code to simulate it.
        UserDTO currentUserDTO = userService.findByUserName("harold@manager.com");

        User user = userMapper.convertToEntity(currentUserDTO);

        List<Project> list = projectRepository.findAllByAssignedManager(user);

        //Here we change the Project List to ProjectDTO List and because ProjectDTO has 2 fields extra, we set these two fields when converting. But we need that task datas for setting. So we create taskService for that.
        return list.stream().map(project -> {

                    ProjectDTO obj = projectMapper.convertToDto(project);

            obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
            obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));

                    return obj;
                }

        ).collect(Collectors.toList());

    }

    @Override
    public List<ProjectDTO> listAllNonCompletedByAssignedManager(UserDTO assignedManager) {

        List<Project> projects = projectRepository
                .findAllByProjectStatusIsNotAndAssignedManager(Status.COMPLETE, userMapper.convertToEntity(assignedManager));
        return projects.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
        }
}
