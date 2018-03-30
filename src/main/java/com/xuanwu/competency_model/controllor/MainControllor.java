package com.xuanwu.competency_model.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainControllor {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

//	@RequestMapping("/login")
//	public String login() {
//		return "login";
//	}

}
