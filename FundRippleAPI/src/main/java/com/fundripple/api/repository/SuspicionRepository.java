package com.fundripple.api.repository;

import com.fundripple.api.model.entity.Payment;
import com.fundripple.api.model.entity.Suspicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SuspicionRepository extends JpaRepository<Suspicion, Long> {

    @Query(value = "SELECT * FROM suspicions where project_name =?1 and user_name=?1",nativeQuery = true)
    Suspicion getSuspicionsByProjectAndUser(String projectName,String userName);
}
