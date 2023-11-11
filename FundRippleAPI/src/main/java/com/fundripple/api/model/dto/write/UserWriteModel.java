package com.fundripple.api.model.dto.write;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWriteModel {
    private String userName;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String description;

}
