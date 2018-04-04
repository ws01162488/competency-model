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

import com.xuanwu.competency_model.common.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description 主要视图转发
 * @author <a href="mailto:miaojiepu@wxchina.com">Jiepu.Miao</a>
 * @date 2018年4月3日
 * @version 1.0.0
 */
@Controller
@Slf4j
public class MainControllor {

	private static final String ATTACHMENT = "attachment";

	@RequestMapping("/view/{path}")
	public String main(@PathVariable("path") String path) {
		return path;
	}

	@RequestMapping(value = "/download")
	public ResponseEntity<byte[]> export(@RequestParam(name = "title") String title,
			@RequestParam(name = "id") String id, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		File file = new File(Constants.ROOTFOLDER + File.separator + id);
		if (!file.exists()) {
			log.error("file {} not exist!", id);
			return null;
		}
		byte[] body = null;
		InputStream is = new FileInputStream(file);
		body = new byte[is.available()];
		is.read(body);
		is.close();
		HttpHeaders headers = new HttpHeaders();
		// 为了解决中文名称乱码问题
		String fileName = new String((title + Constants.EXCELTYPE).getBytes(Constants.DEFAULTCHARSET),
				Constants.ISO_8859_1);
		headers.setContentDispositionFormData(ATTACHMENT, fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		file.delete();
		return new ResponseEntity<byte[]>(body, headers, HttpStatus.CREATED);
	}

}
