package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingDateRepo extends JpaRepository<WorkDate, Integer> {
    List<WorkDate> findByDayWorkBetween(Date startDate, Date endDate);

    Optional<WorkDate> findByDayWork(Date date);
}
