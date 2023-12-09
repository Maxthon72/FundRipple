package com.fundripple.api.model.dto.write;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectWriteModel {
    private String projectName;
    private Double goal;
    private String summery;
    private LocalDate planedDateOfClosing;
}
