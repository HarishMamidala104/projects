package com.ojas.hiring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.dto.CandidateSubStatusDto;
import com.ojas.hiring.service.CandidateSubStatusService;

@RestController
@RequestMapping("/api")
public class CandidateSubStatusController {

    @Autowired
    private CandidateSubStatusService candidateSubStatusService;

    @GetMapping("/substatus")
    public ResponseEntity<List<CandidateSubStatusDto>> getAllSubStatus(@RequestParam String status) {
        List<CandidateSubStatusDto> subStatusList = candidateSubStatusService.getAllSubStatus(status);
        return new ResponseEntity<>(subStatusList, HttpStatus.OK);
    }
}