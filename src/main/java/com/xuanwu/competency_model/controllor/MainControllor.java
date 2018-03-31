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

	// @RequestMapping("/index")
	// public String index() {
	// return "indexPage";
	// }
	//
	// @RequestMapping("/class")
	// public String classes() {
	// return "classPage";
	// }
	//
	// @RequestMapping("/domain")
	// public String domain() {
	// return "domainPage";
	// }

	// @RequestMapping("/login")
	// public String login() {
	// return "login";
	// }

}
