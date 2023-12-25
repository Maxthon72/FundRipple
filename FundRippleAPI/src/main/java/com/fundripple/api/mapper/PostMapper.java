package com.fundripple.api.mapper;

import com.fundripple.api.model.dto.read.PostUnderProjectReadModel;
import com.fundripple.api.model.dto.read.PostUnderUserReadModel;
import com.fundripple.api.model.dto.write.PostUnderProjectWriteModel;
import com.fundripple.api.model.dto.write.PostUnderUserWriteModel;
import com.fundripple.api.model.entity.PostUnderProject;
import com.fundripple.api.model.entity.PostUnderUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy. WARN)
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostUnderProject toEntity(PostUnderProjectWriteModel postUnderProjectWriteModel);

    PostUnderUser toEntity(PostUnderUserWriteModel postUnderUserWriteModel);

    PostUnderProjectReadModel toDto(PostUnderProject postUnderProject);

    PostUnderUserReadModel toDto(PostUnderUser postUnderUser);

    List<PostUnderProjectReadModel> mapProjects(List<PostUnderProject> postUnderProjects);

    List<PostUnderUserReadModel> mapUsers(List<PostUnderUser> postUnderUsers);
}
