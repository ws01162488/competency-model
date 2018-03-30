package com.xuanwu.competency_model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="competency_class")
public class CompetencyClass {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;

}
