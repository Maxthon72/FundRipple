package com.fundripple.api.model.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BenefitReadModel {
    private Long moneyGoal;

    private String description;

    private String name;
    private ProjectReadModel projectReadModel;
}
