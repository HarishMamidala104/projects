package com.ojas.hiring.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.entity.Vendor;
import com.ojas.hiring.repo.VendorRepo;
import com.ojas.hiring.service.VendroService;

@RestController
@RequestMapping("/api")
public class VendorController {

	@Autowired
	VendorRepo vendorRepo;
@Autowired
private	VendroService vendorService;
	@PostMapping("/addVendorDetails")
	public String postVendorDetails(@RequestBody Vendor vendor) {
		LocalDate now = LocalDate.now();
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd MMM, yyyy");
		String format = now.format(ofPattern);
		vendor.setCreatedOn(format);
		vendor.setVisibility(1);
		vendor.setVendor(vendor.getVendor());
		vendorRepo.save(vendor);
		return "Vendor Successfully added";
	}

	@GetMapping("/getVendorDetails")
	public List<Map<String, Object[]>> getVendorDetails() {
		List<Map<String, Object[]>> allVendorDetails = vendorRepo.getAllVendorDetails(1);
		return allVendorDetails;
	}
	
	@DeleteMapping("/deleteVendorById")
	public String deleteVendorById(@RequestParam("id") Integer id) {
		Vendor vendor = vendorRepo.getById(id);
		vendor.setVisibility(0);
		vendorRepo.save(vendor);
		return "Successfully Deleted";
	}
	
	@PutMapping("/updateVendor/{id}")
	public ResponseEntity<String> updateCustomer(@PathVariable("id") int id, @RequestBody Vendor vendor) {
		  try {
		 boolean isUpdated = vendorService.updateVendor(id, vendor);
         if (isUpdated) {
             return ResponseEntity.ok("Vendor updated successfully.");
         } else {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found with ID: " + id);
         }
     } catch (EntityNotFoundException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found with ID: " + id);
     } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the vendor.");
     }
 }
}