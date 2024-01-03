package com.fundripple.api.repository;

import com.fundripple.api.model.entity.Payment;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.ProjectDescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT * FROM payments where project_id = (SELECT project_id FROM projects WHERE project_name=?1)",nativeQuery = true)
    List<Payment> getListOfUsersSupportingProject(String projectName);

    @Query(value = "SELECT * FROM payments where project_id = (SELECT project_id FROM projects WHERE project_name=?1) and user_id=(select user_id from users where user_name = ?2)",nativeQuery = true)
    List<Payment> getPaymentForProjectByUser(String projectName,String userName);
}
