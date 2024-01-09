package com.ecosystem.chomiyeon.service;

import com.ecosystem.chomiyeon.entity.CmyPoll;
import com.ecosystem.chomiyeon.entity.CmyUser;
import com.ecosystem.chomiyeon.payload.CmyPollRequest;
import com.ecosystem.chomiyeon.payload.CmyPollResponse;
import com.ecosystem.chomiyeon.payload.CmyVoteRequest;
import com.ecosystem.chomiyeon.payload.PagedResponse;

public interface ICmyPollService {
    PagedResponse<CmyPollResponse> getAllCmyPolls(CmyUser currentUser, int page, int size);
    PagedResponse<CmyPollResponse> getCmyPollsCreatedBy(String username, CmyUser currentUser, int page, int size);
    PagedResponse<CmyPollResponse> getCmyPollsVotedBy(String username, CmyUser currentUser, int page, int size);
    CmyPoll createCmyPoll(CmyPollRequest cmyPollRequest);
    CmyPollResponse getCmyPollById(Long pollId, CmyUser currentUser);
    CmyPollResponse castVoteAndGetUpdatedPoll(Long pollId, CmyVoteRequest cmyVoteRequest, CmyUser currentUser);
}
