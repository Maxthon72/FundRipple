package com.fundripple.api.repository;

import com.fundripple.api.model.entity.PostUnderProject;
import com.fundripple.api.model.entity.PostUnderUser;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostUnderUserRepository extends JpaRepository<PostUnderUser, Long> {
    List<PostUnderUser> getAllByUser(User user);
}
