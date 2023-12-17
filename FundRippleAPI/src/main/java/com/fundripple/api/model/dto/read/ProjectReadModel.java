package com.fundripple.api.model.dto.read;

import com.fundripple.api.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectReadModel {
    private String projectName;
    private UserReadModel responsibleUser;
    private Double goal;
    private Double moneyCollected;
    private Long numberOfSupporters;
    private LocalDate dateCreated;
    private LocalDate dateClosed;
    private LocalDate planedDateOfClosing;
    private List<ProjectDescriptionReadModel> description;
    private List<TagReadModel> tags;
    private String bannerURL;
    private String summery;
    private String status;
}
