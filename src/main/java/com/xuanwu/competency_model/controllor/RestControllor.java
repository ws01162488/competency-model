package com.xuanwu.competency_model.controllor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanwu.competency_model.common.ExcelExporter;
import com.xuanwu.competency_model.entity.Competency;
import com.xuanwu.competency_model.entity.CompetencyClass;
import com.xuanwu.competency_model.entity.Domain;
import com.xuanwu.competency_model.entity.ExportDto;
import com.xuanwu.competency_model.service.Service;

/**
 * @Description rest接口，提供查询
 * @author <a href="mailto:miaojiepu@wxchina.com">Jiepu.Miao</a>
 * @date 2018年4月3日
 * @version 1.0.0
 */
@RestController
public class RestControllor {
	
	@Autowired
	private Service service;
	
	@RequestMapping("getDomains")
	public List<Domain> getDomains(){
		return service.findAllDomain();
	}
	
	@RequestMapping("getCompetenctClasses")
	public List<CompetencyClass> getCompetenctClasses(){
		return service.findAllCompetencyClasses();
	}
	
	@RequestMapping(value = "/exportWarrper", method = RequestMethod.POST)
	public String exportWarrper(@RequestBody ExportDto dto) throws Exception {
		ExcelExporter<Competency> util = new ExcelExporter<>();
		String fileName = util.exportExcel(dto.getTitle(), dto.getCompetencys());
		return fileName;
	}
	
	@RequestMapping(value = "/exportEvaluation", method = RequestMethod.POST)
	public String exportEvaluation(@RequestBody ExportDto dto) throws Exception {
		ExcelExporter<Competency> util = new ExcelExporter<>();
		String fileName = util.exportExcel(dto.getTitle(), dto.getCompetencys());
		return fileName;
	}
}
