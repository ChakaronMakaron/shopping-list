package com.lemakhno.shopping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.lemakhno.shopping.entities.RoleEntity;

@Transactional(isolation = Isolation.READ_COMMITTED)
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {}
