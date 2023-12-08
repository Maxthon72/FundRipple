package com.fundripple.api.mapper;

import com.fundripple.api.model.dto.read.TagReadModel;
import com.fundripple.api.model.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy. WARN)
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
    TagReadModel toDTO(Tag tag);
    List<TagReadModel> map(List<Tag> tags);
}
