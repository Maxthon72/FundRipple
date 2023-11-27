package com.fundripple.api.model.dto.write;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectWriteModel {
    private String projectName;
    private Long goal;
    private String summery;
    private LocalDateTime planedDateOfClosing;
}
