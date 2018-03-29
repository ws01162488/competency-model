package com.xuanwu.competency_model.controllor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuanwu.competency_model.entity.Domain;
import com.xuanwu.competency_model.service.Service;

@RestController
public class RestControllor {
	
	@Autowired
	private Service service;
	
	@RequestMapping("getDomains")
	public List<Domain> getDomails(){
		return service.findAllDomain();
	}
}
