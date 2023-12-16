package com.fundripple.api.mapper;

import com.fundripple.api.model.dto.read.SubGoalReadModel;
import com.fundripple.api.model.dto.write.SubGoalWriteModel;
import com.fundripple.api.model.entity.SubGoal;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy. WARN)
public interface SubGoalMapper {
    SubGoalMapper INSTANCE = Mappers.getMapper(SubGoalMapper.class);

    SubGoalReadModel toDto(SubGoal subGoal);

    SubGoal toEntity(SubGoalWriteModel subGoalWriteModel);

    List<SubGoalReadModel> mapToDto(List<SubGoal> subGoals);

    List<SubGoal> mapToEntity(List<SubGoalWriteModel> subGoalWriteModels);
}
