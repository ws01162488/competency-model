package com.xuanwu.competency_model.entity;

import java.util.List;

import lombok.Data;

/**
 * @Description 
 * @author <a href="mailto:miaojiepu@wxchina.com">Jiepu.Miao</a>
 * @date 2018年4月4日
 * @version 1.0.0
 */
@Data
public class ExportDto {
	
	private List<Competency> competencys;
	private String title;

}
