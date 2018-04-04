package com.xuanwu.competency_model.controllor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping(value = "/download")
	public ResponseEntity<byte[]> export(@RequestParam(name = "title") String title,
			@RequestParam(name = "id") String id, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		File file = new File("file/" + id + ".xls");
		byte[] body = null;
		InputStream is = new FileInputStream(file);
		body = new byte[is.available()];
		is.read(body);
		is.close();
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String((title + ".xls").getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(body, headers, HttpStatus.CREATED);
	}

}
