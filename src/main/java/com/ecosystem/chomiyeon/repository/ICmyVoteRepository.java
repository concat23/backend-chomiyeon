package com.ecosystem.chomiyeon.repository;

import com.ecosystem.chomiyeon.entity.CmyChoiceVoteCount;
import com.ecosystem.chomiyeon.entity.CmyVote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICmyVoteRepository extends JpaRepository<CmyVote, Long> {
    @Query("SELECT NEW com.ecosystem.chomiyeon.entity.CmyChoiceVoteCount(v.cmyChoice.id, count(v.id)) FROM CmyVote v WHERE v.cmyPoll.id in :pollIds GROUP BY v.cmyChoice.id")
    List<CmyChoiceVoteCount> countByPollIdInGroupByChoiceId(@Param("pollIds") List<Long> pollIds);

    @Query("SELECT NEW com.ecosystem.chomiyeon.entity.CmyChoiceVoteCount(v.cmyChoice.id, count(v.id)) FROM CmyVote v WHERE v.cmyPoll.id = :pollId GROUP BY v.cmyChoice.id")
    List<CmyChoiceVoteCount> countByPollIdGroupByChoiceId(@Param("pollId") Long pollId);

    @Query("SELECT v FROM CmyVote v where v.cmyUser.id = :userId and v.cmyPoll.id in :pollIds")
    List<CmyVote> findByUserIdAndPollIdIn(@Param("userId") Long userId, @Param("pollIds") List<Long> pollIds);

    @Query("SELECT v FROM CmyVote v where v.cmyUser.id = :userId and v.cmyPoll.id = :pollId")
    CmyVote findByUserIdAndPollId(@Param("userId") Long userId, @Param("pollId") Long pollId);

    @Query("SELECT COUNT(v.id) from CmyVote v where v.cmyUser.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT v.cmyPoll.id FROM CmyVote v WHERE v.cmyUser.id = :userId")
    Page<Long> findVotedPollIdsByUserId(@Param("userId") Long userId, Pageable pageable);

}
