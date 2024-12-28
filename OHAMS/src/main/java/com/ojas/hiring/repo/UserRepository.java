package com.ojas.hiring.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByemailaddress(String emailaddress);

	@Query(value = "select * from user_dtls where employee_id=?1 and visibility=1",nativeQuery = true)
	Optional<User> findByemployeeId(long employeeId);
	
	Optional<User> findByuserName(String userName);
	
	@Query(value = "select * from user_dtls where user_name=?1",nativeQuery = true)
	public User getByUserName(String userName);
	
	@Query(value = "select * from user_dtls where visibility=?1 order by id desc",nativeQuery = true)
	public List<User> getAllUserDetails(int visibility);

	@Query(value = " select employee_id from user_dtls", nativeQuery = true)
	public List<String> getEmployeeIds();
	
	@Query(value = " select email from user_dtls where visibility=1", nativeQuery = true)
	public List<String> getEmployeeEmailIds();
	
	@Query(value = " select user_name from user_dtls", nativeQuery = true)
	public List<String> getEmployeeNames();

	@Query(value = "select * from user_dtls where role in ('TAG_EXECUTIVE','TAG_LEAD') AND visibility=1", nativeQuery = true)
	public List<User> getTagUsers();

}
