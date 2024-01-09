package com.ecosystem.chomiyeon.repository;

import com.ecosystem.chomiyeon.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdminUserTokenRepository extends JpaRepository<Token,Long> {
}
