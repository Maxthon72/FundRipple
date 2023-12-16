package com.fundripple.api.model.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubGoalReadModel {
    private Long moneyGoal;

    private String description;

    private String name;

    private Boolean met;
}
