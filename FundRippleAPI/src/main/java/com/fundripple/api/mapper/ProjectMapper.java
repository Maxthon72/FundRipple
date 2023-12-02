package com.fundripple.api.mapper;

import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.read.ProjectSLElement;
import com.fundripple.api.model.dto.read.UserReadModel;
import com.fundripple.api.model.dto.write.ProjectWriteModel;
import com.fundripple.api.model.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy. WARN)
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    Project toEntity(ProjectWriteModel projectWriteModel);
    Project toEntity(ProjectSLElement projectSLElement);

    ProjectReadModel toReadModel(Project project);

    //void updateProjectFromDto(ProjectWriteModel projectWriteModel);

    List<ProjectReadModel> map(List<Project> projectList);

    ProjectSLElement toSLElementModel(Project project);

    //@Mapping(source = "userName",target = "userName")
    List<ProjectSLElement> mapSLE(List<Project> projectList);
}
