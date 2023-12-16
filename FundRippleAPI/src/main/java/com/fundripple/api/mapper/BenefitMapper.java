package com.fundripple.api.mapper;

import com.fundripple.api.model.dto.read.BenefitReadModel;
import com.fundripple.api.model.dto.write.BenefitWriteModel;
import com.fundripple.api.model.entity.Benefit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy. WARN,
        uses = {ProjectMapper.class})
public interface BenefitMapper {
    BenefitMapper INSTANCE = Mappers.getMapper(BenefitMapper.class);

    Benefit toEntity(BenefitWriteModel benefitWriteModel);

    BenefitReadModel toDto(Benefit benefit);

    List<BenefitReadModel> mapToDto(List<Benefit> benefits);
    List<Benefit> mapToEntity(List<BenefitWriteModel> benefitWriteModels);
}
