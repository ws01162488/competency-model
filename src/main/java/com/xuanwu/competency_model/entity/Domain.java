package com.xuanwu.competency_model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Domain {

	@Id
	@GeneratedValue
	private long id;
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "domain_competency", joinColumns = { @JoinColumn(name = "domain_id") }, inverseJoinColumns = {
			@JoinColumn(name = "competency_id") })
//	@ManyToMany(targetEntity = Competency.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//	@JoinTable(name = "domain_competency", joinColumns = @JoinColumn(name = "domain_id", referencedColumnName = "id"), 
//	inverseJoinColumns = @JoinColumn(name = "competency_id", referencedColumnName = "id"))
	private List<Competency> competencys;

}