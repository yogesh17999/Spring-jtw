package com.spring.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.demo.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{

}
