package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImp implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImp(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        return null;
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

    }

    @Override
    public void delete(String code) {

    }
}