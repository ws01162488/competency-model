package com.xuanwu.competency_model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xuanwu.competency_model.entity.Competency;

public interface CompetencyRepository extends JpaRepository<Competency, Long>{
	Competency findByName(String name);
}
