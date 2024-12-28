package com.ojas.hiring.dto;

import com.ojas.hiring.entity.UserHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserHistoryDTO {

    private int id;
    private int userId;
    private long employeeId;
    private String userName;
    private String emailAddress;
    private String role;
    private String startDate;
    private String endDate;
    private String duration;

    public UserHistory toUserHistory() {
        UserHistory userHistory = new UserHistory();
        userHistory.setId(this.id);
        userHistory.setUser_id(this.userId);
        userHistory.setEmployeeId(this.employeeId);
        userHistory.setUserName(this.userName);
        userHistory.setEmailaddress(this.emailAddress);
        userHistory.setRole(this.role);
        userHistory.setStartDate(this.startDate);
        userHistory.setEndDate(this.endDate);
        userHistory.setDuration(this.duration);
        return userHistory;
    }
}
