package com.ojas.hiring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.hiring.entity.Skill;

public interface SkillRepo extends JpaRepository<Skill, Integer> {

}
