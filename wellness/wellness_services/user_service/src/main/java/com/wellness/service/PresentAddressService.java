package com.wellness.service;

import com.wellness.entities.PresentAddress;

public interface PresentAddressService {
	
	public PresentAddress getAddressByuserId(long userId);
}
