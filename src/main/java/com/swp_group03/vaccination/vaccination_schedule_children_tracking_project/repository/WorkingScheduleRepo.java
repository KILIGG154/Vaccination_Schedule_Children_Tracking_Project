package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.repository;

import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkScheduleStatus;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingScheduleRepo extends JpaRepository<WorkingSchedule, Integer> {
    
    /**
     * Tìm các lịch làm việc theo ngày làm việc
     */
    List<WorkingSchedule> findBySchedule(WorkDate workDate);
    
    /**
     * Tìm các lịch làm việc theo ngày làm việc và trạng thái
     */
    List<WorkingSchedule> findByScheduleAndWorkStatus(WorkDate workDate, WorkScheduleStatus workStatus);
    
    /**
     * Tìm lịch làm việc theo ngày làm việc và nhân viên
     */
    Optional<WorkingSchedule> findByScheduleAndAccount(WorkDate workDate, Account account);
    
    /**
     * Đếm số lượng lịch làm việc theo tài khoản và trạng thái
     */
    long countByAccountAndWorkStatus(Account account, WorkScheduleStatus workStatus);
    
    /**
     * Tìm các lịch làm việc theo tài khoản
     */
    List<WorkingSchedule> findByAccount(Account account);
    
    /**
     * Tìm các lịch làm việc theo dateId
     */
    @Query("SELECT ws FROM WorkingSchedule ws WHERE ws.schedule.dateId = :dateId")
    List<WorkingSchedule> findByDateId(@Param("dateId") int dateId);
    
    /**
     * Tìm các lịch làm việc theo accountId
     */
    @Query("SELECT ws FROM WorkingSchedule ws WHERE ws.account.accountId = :accountId")
    List<WorkingSchedule> findByAccountId(@Param("accountId") String accountId);
    
    /**
     * Tìm lịch làm việc theo accountId và dateId
     */
    @Query("SELECT ws FROM WorkingSchedule ws WHERE ws.account.accountId = :accountId AND ws.schedule.dateId = :dateId")
    Optional<WorkingSchedule> findByAccountIdAndDateId(@Param("accountId") String accountId, @Param("dateId") int dateId);
}
