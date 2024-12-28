package com.ojas.hiring.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.entity.PMO;

@Repository
public interface PMORepo extends JpaRepository<PMO, Integer> {

	Optional<PMO> findByEmployeeId(String eMPLOYEE_ID);

	Optional<PMO> findByEmployeeName(String eMPLOYEE_NAME);

	Optional<PMO> findByEmployeeType(String eMPLOYEE_TYPE);

	Optional<PMO> findByProjectCategory(String pROJECT_CATAGORY);

	Optional<PMO> findByClientName(String cLIENT_NAME);

	Optional<PMO> findByProjectName(String pROJECT_NAME);

	Optional<PMO> findByProjectStatus(String pROJECT_STATUS);

	Optional<PMO> findByProjectStartDate(String pROJECT_START_DATE);

	Optional<PMO> findByProjectEndDate(String pROJECT_END_DATE);

	Optional<PMO> findByDepartment(String dEPARTMENT);

	Optional<PMO> findByNetWorkingDays(String nETWORKING_DAYS);

	Optional<PMO> findByRemarks(String rEMARKS);

	@Query(value = "SELECT * FROM data_pmo WHERE EMPLOYEE_TYPE=?1 AND EMPLOYEE_ID=?2", nativeQuery = true)
	Optional<PMO> findByEmployeeTypeAndEmployeeId(String eMPLOYEE_TYPE, String eMPLOYEE_ID);

	@Query(value = "SELECT * FROM data_pmo WHERE PROJECT_CATAGORY=?1 AND EMPLOYEE_ID=?2", nativeQuery = true)
	Optional<PMO> findByProjectCategoryAndEmployeeId(String pROJECT_CATAGORY, String eMPLOYEE_ID);

	@Query(value = "SELECT * FROM data_pmo WHERE CLIENT_NAME=?1 AND EMPLOYEE_ID=?2", nativeQuery = true)
	Optional<PMO> findByClientNameAndEmployeeId(String cLIENT_NAME, String eMPLOYEE_ID);

	@Query(value = "SELECT * FROM data_pmo WHERE pROJECT_NAME=?1 AND EMPLOYEE_ID=?2", nativeQuery = true)
	Optional<PMO> findByProjectNameAndEmployeeId(String pROJECT_NAME, String eMPLOYEE_ID);

	@Query(value = "SELECT * FROM data_pmo WHERE PROJECT_STATUS=?1 AND EMPLOYEE_ID=?2", nativeQuery = true)
	Optional<PMO> findByProjectStatusAndEmployeeId(String pROJECT_STATUS, String eMPLOYEE_ID_FORMAT);

	@Query(value = "SELECT * FROM data_pmo WHERE PROJECT_START_DATE=?1 AND EMPLOYEE_ID=?2", nativeQuery = true)
	Optional<PMO> findByProjectStartDateAndEmployeeId(String pROJECT_START_DATE, String eMPLOYEE_ID_FORMAT);

	@Query(value = "SELECT * FROM data_pmo WHERE PROJECT_END_DATE=?1 AND EMPLOYEE_ID=?2", nativeQuery = true)
	Optional<PMO> findByProjectEndDateAndEmployeeId(String pROJECT_END_DATE, String eMPLOYEE_ID_FORMAT);

	@Query(value = "SELECT * FROM data_pmo WHERE DEPARTMENT=?1 AND EMPLOYEE_ID=?2", nativeQuery = true)
	Optional<PMO> findByDepartmentAndEmployeeId(String dEPARTMENT, String eMPLOYEE_ID_FORMAT);

	@Query(value = "SELECT * FROM data_pmo WHERE NETWORKING_DAYS=?1 AND EMPLOYEE_ID=?2", nativeQuery = true)
	Optional<PMO> findByNetWorkingDaysAndEmployeeId(String nET_WORKING_DAYS_FORMAT, String eMPLOYEE_ID);

	@Query(value = "SELECT * FROM data_pmo WHERE REMARKS=?1 AND EMPLOYEE_ID=?2", nativeQuery = true)
	Optional<PMO> findByRemarksAndEmployeeId(String rEMARKS, String eMPLOYEE_ID_FORMAT);

}
