package com.xuanwu.competency_model.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 主要视图转发
 * @author <a href="mailto:miaojiepu@wxchina.com">Jiepu.Miao</a>
 * @date 2018年4月3日
 * @version 1.0.0
 */
@Controller
public class MainControllor {

	@RequestMapping("/view/{path}")
	public String main(@PathVariable("path") String path) {
		return path;
	}

}
