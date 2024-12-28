package com.ojas.hiring.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ojas.hiring.entity.Vendor;

public interface VendorRepo extends JpaRepository<Vendor, Integer>{

	@Query(value = "SELECT * FROM vendor_reference WHERE visibility = ?1 ORDER BY id DESC", nativeQuery = true)
	public List<Map<String,Object[]>> getAllVendorDetails(int visibility);
	
	@Query(value="SELECT * FROM vendor_reference where vendor = ?1",nativeQuery = true)
	public String findByVendorName(String name);
	
	
	@Query(value="select * from vendor_reference where id=?1",nativeQuery = true)
	 Optional<Vendor>   getId(long id);



}
