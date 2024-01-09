package com.ecosystem.chomiyeon.repository;

import com.ecosystem.chomiyeon.entity.CmyPoll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICmyPollRepository extends JpaRepository<CmyPoll,Long> {
    Optional<CmyPoll> findById(Long pollId);

    Page<CmyPoll> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<CmyPoll> findByIdIn(List<Long> pollIds);

    List<CmyPoll> findByIdIn(List<Long> pollIds, Sort sort);
}
