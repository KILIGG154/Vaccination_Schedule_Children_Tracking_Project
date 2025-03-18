package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.working;

import com.fasterxml.jackson.annotation.JsonInclude;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.Account;
//import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.entity.WorkDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkingResponse {
    private int dateId;
    private String accountId;
    private WorkDateDTO date;
//    private Account account;
    private String status;

}
