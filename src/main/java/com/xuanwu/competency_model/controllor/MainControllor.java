package com.xuanwu.competency_model.controllor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuanwu.competency_model.service.Service;

@Controller
public class MainControllor {

	@Autowired
	private Service service;

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "Hello World";
	}

	@RequestMapping("/")
	public String index(ModelMap map) {
		map.addAttribute("host", service.findAllDomain());
		return "index";
	}

}
