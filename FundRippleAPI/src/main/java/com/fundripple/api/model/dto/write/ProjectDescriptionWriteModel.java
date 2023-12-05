package com.fundripple.api.model.dto.write;

import com.fundripple.api.model.enums.ProjectDescriptionElementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDescriptionWriteModel {

    private Long projectId;
    private Long indexIdDescription;
    private String description;
    private String type;
}
