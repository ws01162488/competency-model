package com.xuanwu.competency_model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	private String name;
	private String definition;
	private String description;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "class_id")
	private CompetencyClass competencyClass;

}
