package com.ojas.hiring.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.hiring.entity.PMO;
import com.ojas.hiring.service.PMOService;

@RestController
@RequestMapping("/api")
public class PMOController {

	@Autowired
	private PMOService service;

	@PostMapping("/savePmo")
	public PMO savePmo(@RequestBody PMO pmo) {
		return service.savePmo(pmo);
	}

	@GetMapping("/getAllPmo")
	public List<PMO> getAllPmo() {
		return service.getAllPmo();
	}

	@DeleteMapping("/deleteAllPmo")
	public String deleteAllPmo() {
		String response = service.deleteAllPmo();

		return response;
	}

	@PutMapping("/updatePmo")
	public PMO updatePmo(@RequestParam("pmoId") String pmoId, @RequestBody PMO pmo) {
		PMO updatedPMO = service.updatePmo(pmoId, pmo);
		return updatedPMO;
	}

	@PostMapping("/pmoExcelFileUpload")
	public void bulkUpload(@RequestParam("file") MultipartFile excelFile) throws IOException {
		service.bulkUpload(excelFile);
	}

}
