package com.ecosystem.chomiyeon.repository;


import com.ecosystem.chomiyeon.entity.CmyEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICmyEmployeeRepository extends JpaRepository<CmyEmployee,Long> {
}
