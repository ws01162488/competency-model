package com.xuanwu.competency_model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.xuanwu.competency_model.annotation.ExcelFieldName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 对应competency表，胜任力
 * @author <a href="mailto:miaojiepu@wxchina.com">Jiepu.Miao</a>
 * @date 2018年4月3日
 * @version 1.0.0
 */
@Entity
@Data
@NoArgsConstructor
public class Competency {

	@Id
	@GeneratedValue
	private long id;
	@ExcelFieldName(label = "胜任力", index = 1, width = 6000)
	private String name;
	@ExcelFieldName(label = "定义", index = 2, width = 20000)
	private String definition;
	@ExcelFieldName(label = "行为描述", index = 3, width = 20000)
	private String description;
	@ExcelFieldName(label = "评价标准", index = 4, width = 10000)
	private String evaluationCriterion;
	@ExcelFieldName(label = "面试问题", index = 5, width = 18000)
	private String question;
	@ExcelFieldName(label = "作答记录及评分", index = 6, width = 10000)
	@Transient
	private String answerRecord = "作答记录：\r\n\r\n\r\n\r\n评分：□5  □4  □3  □2  □1";
//	@ExcelFieldName(label = "评分(1-5分)", index = 7, width = 10000)
//	@Transient
//	private String score;
}
