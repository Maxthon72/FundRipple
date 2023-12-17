package com.fundripple.api.model.dto.write;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubGoalWriteModel {
    private Long id;
    private Long moneyGoal;

    private String description;

    private String name;
}
