package com.xuanwu.competency_model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xuanwu.competency_model.entity.CompetencyClass;
import com.xuanwu.competency_model.entity.Domain;
import com.xuanwu.competency_model.repository.CompetencyClassRepository;
import com.xuanwu.competency_model.repository.DomainRepository;

/**
 * @Description 主要service，提供信息查询
 * @author <a href="mailto:miaojiepu@wxchina.com">Jiepu.Miao</a>
 * @date 2018年4月3日
 * @version 1.0.0
 */
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
