package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;


import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Child;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepo extends JpaRepository<Child, Integer> {
    List<Child> findByNameContainingIgnoreCase(String name);

//    Child updateById(int id, ChildrenRequest request);

//    List<Child> findBy_AccountId(String accountId);
}
