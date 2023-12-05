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

    private Role role;

    public UserReadModel(User user){
        this.userName=user.getUsername();
        this.email=user.getEmail();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.description=user.getDescription();
        this.role=user.getRole();
    }
}
