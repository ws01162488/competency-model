package com.xuanwu.competency_model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xuanwu.competency_model.entity.Domain;

public interface DomainRepository extends JpaRepository<Domain, Long> {
}
