package com.ecosystem.chomiyeon.repository;

import com.ecosystem.chomiyeon.entity.CmyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICmyUserRepository extends JpaRepository<CmyUser,Long> {
    Optional<CmyUser> findByEmail(String email);

    Optional<CmyUser> findByUsernameOrEmail(String username, String email);

    List<CmyUser> findByIdIn(List<Long> userIds);

    Optional<CmyUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
