package com.ojas.hiring.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ojas.hiring.dto.LocationsDTO;
import com.ojas.hiring.entity.Locations;
import com.ojas.hiring.exceptions.BaseErrors;
import com.ojas.hiring.exceptions.BaseException;
import com.ojas.hiring.exceptions.LocationNotFoundException;
import com.ojas.hiring.repo.LocationRepo;
import com.ojas.hiring.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepo locationRepo;

	@Override
	public String addLocationDetails(LocationsDTO locations) {
		System.out.println("Received location: " + locations.getLocation());

		String existingLocation = locationRepo.findByLocationname(locations.getLocation());

		if (existingLocation != null) {
			throw new LocationNotFoundException("Location already exists. Try with another name.");
		}

		// If the location doesn't exist, save the new location
		Locations location = new Locations();
		location.setLocation(locations.getLocation());
		location.setVisibility(1);

		// Save the location and check if the save was successful
		Locations savedLocation = locationRepo.save(location);
		if (savedLocation != null && savedLocation.getId() > 0) {
			return "Successfully added Location";
		} else {
			return "Failed to add Location. Please try again.";
		}
	}

	@Override
	public List<LocationsDTO> getLocationDetails() {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		List<Locations> allLocations = locationRepo.findAll(sort);
		if (allLocations != null)
			return allLocations.stream().filter(location -> location.getVisibility() == 1).map(location -> {
				LocationsDTO dto = new LocationsDTO();
				dto.setId(location.getId());
				dto.setLocation(location.getLocation());
				return dto;
			}).toList();
		else
			throw new BaseException(BaseErrors.NO_DATA_FOUND);
	}

	@Override
	public List<LocationsDTO> getLocationDetails(int offset, int limit, String sortby) {
		PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, sortby));
		List<Locations> allLocations = locationRepo.findAll(pageRequest).getContent();
		return allLocations.stream().filter(location -> location.getVisibility() == 1).map(location -> {
			LocationsDTO dto = new LocationsDTO();
			dto.setId(location.getId());
			dto.setLocation(location.getLocation());
			return dto;
		}).toList();
	}

	@Transactional
	@Override
	public String deleteLocation(Integer id) {

		Locations location = locationRepo.findById(id)
				.orElseThrow(() -> new LocationNotFoundException("Location not found with id: " + id));

		if (location.getVisibility() != 0) {
			location.setVisibility(0);
			locationRepo.save(location);
			return "Successfully deleted Location";
		} else {

			throw new LocationNotFoundException("Location not found with given id: " + id);
		}

	}

	@Override
	public String updateLocation(int id, LocationsDTO locations) {
		Optional<Locations> byId = locationRepo.findById(id);
		Locations locations2 = byId.get();
		String existingLocation = locationRepo.findByLocationname(locations.getLocation());

		if (existingLocation != null) {
			throw new LocationNotFoundException("Location already exists. Try with another name.");
		}
		locations2.setLocation(locations.getLocation());
		Locations savedLocation = locationRepo.save(locations2);
		if (savedLocation != null) {
			return "Location updated successfully.";
		} else {
			return "Failed to update the Location";
		}

	}
}
