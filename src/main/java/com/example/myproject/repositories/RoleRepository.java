package com.example.myproject.repositories;

import com.example.myproject.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByRoleName(String roleName);

}
