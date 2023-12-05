package com.fundripple.api.mapper;

import com.fundripple.api.model.dto.read.ProjectDescriptionReadModel;
import com.fundripple.api.model.dto.write.ProjectDescriptionWriteModel;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.ProjectDescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy. WARN)
public interface ProjectDescriptionMapper {
    ProjectDescriptionMapper INSTANCE = Mappers.getMapper(ProjectDescriptionMapper.class);
    @Mapping(target = "id", ignore = true)
    ProjectDescription toEntity(ProjectDescriptionWriteModel projectDescriptionWriteModel);

    ProjectDescriptionReadModel toReadModel(ProjectDescription projectDescription);

    List<ProjectDescriptionReadModel> map(List<ProjectDescription> projectDescriptions);

}
