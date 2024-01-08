package com.ecosystem.chomiyeon.repository;

import com.ecosystem.chomiyeon.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAdminUserRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByEmail(String email);

    Optional<AdminUser> findByUsernameOrEmail(String username, String email);

    List<AdminUser> findByIdIn(List<Long> userIds);

    Optional<AdminUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
