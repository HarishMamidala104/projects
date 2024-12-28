package com.ojas.hiring.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.Customer;
import com.ojas.hiring.entity.Vendor;
import com.ojas.hiring.repo.VendorRepo;

@Service
public class VendroService {
	@Autowired
	private VendorRepo vendorRepository;

	public boolean updateVendor(int id, Vendor updatedVendor) {
		Optional<Vendor> optionalVendor = vendorRepository.getId(id);

		LocalDate now = LocalDate.now();
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd MMM, yyyy");
		String format = now.format(ofPattern);
		if (optionalVendor.isPresent()) {
			Vendor existingVendor = optionalVendor.get();
			existingVendor.setVendor(updatedVendor.getVendor());
			vendorRepository.save(existingVendor);
			return true;
		} else {
			return false;
		}
	}
}