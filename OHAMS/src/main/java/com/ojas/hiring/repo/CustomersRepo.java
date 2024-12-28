package com.ojas.hiring.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ojas.hiring.entity.Customer;
import com.ojas.hiring.entity.RRF;

public interface CustomersRepo extends JpaRepository<Customer, Integer> {
	
	@Query(value = "select * from customer_details where visibility = ?1  ORDER BY id DESC",nativeQuery = true)
	public List<Map<String, Object[]>> getCustomerData(int visibility);
	
	@Query(value = "select * from customer_details where customer_name = ?1 and visibility=1",nativeQuery = true)
	public String findByName(String  name);

    @Query(value ="select customer_name from customer_details where customer_type = ?1",nativeQuery = true)
    public List<String> getAllInternalCustomers(String type);
    
	@Query(value="select * from customer_details where id=?1",nativeQuery = true)
	 Optional<Customer>   getId(long id);
}
