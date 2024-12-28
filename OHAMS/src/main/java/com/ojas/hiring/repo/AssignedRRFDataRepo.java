package com.ojas.hiring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ojas.hiring.entity.AssignedRRFData;

public interface AssignedRRFDataRepo extends JpaRepository<AssignedRRFData, Integer>{
	
  @Query(value = "select * from assigned_rrf_data where rrf_id=?1",nativeQuery = true)
  List<AssignedRRFData> getByrrfId(long rrfId);
	
}
