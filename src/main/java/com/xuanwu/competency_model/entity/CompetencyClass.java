package com.xuanwu.competency_model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	@Transient
	private List<Competency> competencys = new ArrayList<>();

}
