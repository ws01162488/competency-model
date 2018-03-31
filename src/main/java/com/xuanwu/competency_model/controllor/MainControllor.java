package com.xuanwu.competency_model.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainControllor {

	@RequestMapping("/view/{path}")
	public String main(@PathVariable("path") String path) {
		return path;
	}

}
