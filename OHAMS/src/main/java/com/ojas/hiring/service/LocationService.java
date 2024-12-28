package com.ojas.hiring.service;

import java.util.List;

import com.ojas.hiring.dto.LocationsDTO;

public interface LocationService {
	String addLocationDetails(LocationsDTO locations);

	List<LocationsDTO> getLocationDetails();

	List<LocationsDTO> getLocationDetails(int offset, int limit, String sortby);

	String updateLocation(int id, LocationsDTO locations);

	String deleteLocation(Integer id);
}
