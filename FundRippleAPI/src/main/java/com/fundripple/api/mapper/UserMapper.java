package com.fundripple.api.mapper;

import com.fundripple.api.model.dto.read.UserReadModel;
import com.fundripple.api.model.dto.read.UserSLElement;
import com.fundripple.api.model.dto.write.UserWriteModel;
import com.fundripple.api.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.control.MappingControl;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy. WARN)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserWriteModel userWriteModel);
    User toEntity(UserSLElement userSLElement);


    default UserReadModel toReadModel(User user){
        return new UserReadModel(user.getUsername(), user.getEmail(), user.getFirstName(),user.getLastName(),user.getDescription(),user.getRole());
    }
    void updateUserFromDto(UserWriteModel userWriteModel, @MappingTarget User user);

    List<UserReadModel> map(List<User> users);

    default UserSLElement toSLElement(User user){
        UserSLElement userSLElement = new UserSLElement();
        userSLElement.setUserName(user.getUsername());
        return userSLElement;
    }
    List<UserSLElement> mapToSLE(List<User> users);

}
