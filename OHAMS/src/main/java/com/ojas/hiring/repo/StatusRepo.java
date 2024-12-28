package com.ojas.hiring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.hiring.entity.Status;

public interface StatusRepo extends JpaRepository<Status, Integer>{

}
