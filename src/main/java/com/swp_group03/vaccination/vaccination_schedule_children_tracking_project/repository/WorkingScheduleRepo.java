package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingSchedule;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingScheduleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingScheduleRepo extends JpaRepository<WorkingSchedule, WorkingScheduleId> {
    /**
     * Tìm danh sách lịch làm việc theo ID của tài khoản
     * @param accountId ID của tài khoản
     * @return Danh sách lịch làm việc
     */
    List<WorkingSchedule> findByAccountId(String accountId);
    
    /**
     * Tìm danh sách lịch làm việc theo ID của ngày làm việc
     * @param dateId ID của ngày làm việc
     * @return Danh sách lịch làm việc
     */
    List<WorkingSchedule> findByDateId(int dateId);
    
    /**
     * Tìm danh sách lịch làm việc theo ID của tài khoản và ID của ngày làm việc
     * @param accountId ID của tài khoản
     * @param dateId ID của ngày làm việc
     * @return Danh sách lịch làm việc
     */
    List<WorkingSchedule> findByAccountIdAndDateId(String accountId, int dateId);
}
