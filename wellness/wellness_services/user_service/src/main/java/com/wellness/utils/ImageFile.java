package com.wellness.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class ImageFile {

	@Autowired
	private ImageNameGenerator generator;

	public boolean isImageFile(MultipartFile file) {
		// Define the allowed image extensions
		List<String> allowedExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");

		String fileExtension = generator.getFileExtensionName(file.getOriginalFilename());
		return allowedExtensions.contains(fileExtension.toLowerCase());
	}
}
