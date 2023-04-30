package com.lemakhno.shopping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.lemakhno.shopping.entities.UserEntity;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    
    public UserEntity findByEmail(String email);
}
