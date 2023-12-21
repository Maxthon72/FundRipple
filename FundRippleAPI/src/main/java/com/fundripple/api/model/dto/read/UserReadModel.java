package com.fundripple.api.model.dto.read;

import com.fundripple.api.model.entity.User;
import com.fundripple.api.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReadModel {

    private String userName;

    private String email;

    private String firstName;

    private String lastName;

    private String description;

    private String role;
}
