package com.fundripple.api.model.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSLElement {
    private String projectName;
    private UserSLElement responsibleUser;
    private Double goal;
    private Double moneyCollected;
    private Long numberOfSupporters;
    private LocalDateTime dateCreated;
    private LocalDateTime planedDateOfClosing;
    private String summery;
}
