package com.ecosystem.chomiyeon.repository;

import com.ecosystem.chomiyeon.entity.AdminRole;
import com.ecosystem.chomiyeon.enumaration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAdminRoleRepository extends JpaRepository<AdminRole, Long> {
    Optional<AdminRole> findByName(RoleName roleName);
}
