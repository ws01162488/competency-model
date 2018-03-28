package com.xuanwu.competency_model.controllor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllor {

	@RequestMapping("*")
	public String hello(){
		return "hello";
	}
}
