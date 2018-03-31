package com.xuanwu.competency_model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuanwu.competency_model.entity.CompetencyClass;
import com.xuanwu.competency_model.entity.Domain;
import com.xuanwu.competency_model.repository.CompetencyClassRepository;
import com.xuanwu.competency_model.repository.DomainRepository;

@Component
public class Service {

	@Autowired
	private DomainRepository domainRepository;
	
	@Autowired
	private CompetencyClassRepository competencyClassRepository;

	public List<Domain> findAllDomain() {
		return domainRepository.findAll();
	}
	
	public List<CompetencyClass> findAllCompetencyClasses() {
		return competencyClassRepository.findAll();
	}

}
