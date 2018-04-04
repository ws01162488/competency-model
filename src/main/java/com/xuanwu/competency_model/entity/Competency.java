package com.xuanwu.competency_model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	@ExcelFieldName(label="胜任力",index=0)
	private String name;
	@ExcelFieldName(label="定义",index=1)
	private String definition;
	@ExcelFieldName(label="行为描述",index=2)
	private String description;

}
