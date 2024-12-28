package com.ojas.hiring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ojas.hiring.entity.UserHistory;

public interface UserHistoryRepo extends JpaRepository<UserHistory, Integer> {

	@Query(value = "select e.* from user_history e join ( Select MAX(id) AS latest_id from user_history) t on e.id = t.latest_id", nativeQuery = true)
	public UserHistory getByEmployeeId();
	
	@Query(value = "select * from user_history where user_id =?1",nativeQuery = true)
	public List<UserHistory> getUserRolesHistoryByEmployeeId(long id);
}
