package com.fundripple.api.model.dto.read;

import com.fundripple.api.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectReadModel {
    private String projectName;
    private UserReadModel responsibleUser;
    private Long goal;
    private Long moneyCollected;
    private Long numberOfSupporters;
    private LocalDateTime dateCreated;
    private LocalDateTime dateClosed;
    private LocalDateTime planedDateOfClosing;
    //private List<DescriptionReadModel> description;
    private String summery;
    private String status;
}
