package com.xuanwu.competency_model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

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

	public Competency(String name, String definition, String description) {
		this.name = name;
		this.definition = definition;
		this.description = description;
	}

}
