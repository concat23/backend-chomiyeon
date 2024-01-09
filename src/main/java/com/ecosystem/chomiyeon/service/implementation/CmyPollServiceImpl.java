package com.ecosystem.chomiyeon.service.implementation;

import com.ecosystem.chomiyeon.constant.AppConstants;
import com.ecosystem.chomiyeon.entity.*;
import com.ecosystem.chomiyeon.exception.BadRequestException;
import com.ecosystem.chomiyeon.exception.ResourceNotFoundException;
import com.ecosystem.chomiyeon.mapper.ModelMapper;
import com.ecosystem.chomiyeon.payload.CmyPollRequest;
import com.ecosystem.chomiyeon.payload.CmyPollResponse;
import com.ecosystem.chomiyeon.payload.CmyVoteRequest;
import com.ecosystem.chomiyeon.payload.PagedResponse;
import com.ecosystem.chomiyeon.repository.ICmyPollRepository;
import com.ecosystem.chomiyeon.repository.ICmyUserRepository;
import com.ecosystem.chomiyeon.repository.ICmyVoteRepository;
import com.ecosystem.chomiyeon.service.ICmyPollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CmyPollServiceImpl implements ICmyPollService {

    @Autowired
    private ICmyPollRepository iCmyPollRepository;

    @Autowired
    private ICmyVoteRepository iCmyVoteRepository;

    @Autowired
    private ICmyUserRepository iCmyUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(CmyPollServiceImpl.class);

    @Override
    public PagedResponse<CmyPollResponse> getAllCmyPolls(CmyUser currentUser, int page, int size) {
        try {
            logger.info("Fetching all polls for user: {}, page: {}, size: {}", currentUser.getUsername(), page, size);

                validatePageNumberAndSize(page, size);

                // Retrieve Polls
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
                Page<CmyPoll> polls = this.iCmyPollRepository.findAll(pageable);

                if(polls.getNumberOfElements() == 0) {
                    return new PagedResponse<>(Collections.emptyList(), polls.getNumber(),
                            polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
                }

                // Map Polls to PollResponses containing vote counts and poll creator details
                List<Long> pollIds = polls.map(CmyPoll::getId).getContent();
                Map<Long, Long> choiceVoteCountMap = getCmyChoiceVoteCountMap(pollIds);
                Map<Long, Long> pollUserVoteMap = getCmyPollUserVoteMap(currentUser, pollIds);
                Map<Long, CmyUser> creatorMap = getCmyPollCreatorMap(polls.getContent());

                List<CmyPollResponse> pollResponses = polls.map(poll -> {
                    return ModelMapper.mapCmyPollToCmyPollResponse(poll,
                            choiceVoteCountMap,
                            creatorMap.get(poll.getCreatedBy()),
                            pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null));
                }).getContent();

                return new PagedResponse<>(pollResponses, polls.getNumber(),
                        polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        } catch (Exception ex) {
            logger.error("Error fetching all polls", ex);
            throw ex; // Adjust this according to your exception handling strategy.
        }

    }

    @Override
    public PagedResponse<CmyPollResponse> getCmyPollsCreatedBy(String username, CmyUser currentUser, int page, int size) {
        try {
            logger.info("Fetching polls created by user: {}, page: {}, size: {}", username, page, size);

            validatePageNumberAndSize(page, size);

                CmyUser user = this.iCmyUserRepository.findByUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

                // Retrieve all polls created by the given username
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
                Page<CmyPoll> polls = this.iCmyPollRepository.findByCreatedBy(user.getId(), pageable);

                if (polls.getNumberOfElements() == 0) {
                    return new PagedResponse<>(Collections.emptyList(), polls.getNumber(),
                            polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
                }

                // Map Polls to PollResponses containing vote counts and poll creator details
                List<Long> pollIds = polls.map(CmyPoll::getId).getContent();
                Map<Long, Long> choiceVoteCountMap = getCmyChoiceVoteCountMap(pollIds);
                Map<Long, Long> pollUserVoteMap = getCmyPollUserVoteMap(currentUser, pollIds);

                List<CmyPollResponse> pollResponses = polls.map(poll -> {
                    return ModelMapper.mapCmyPollToCmyPollResponse(poll,
                            choiceVoteCountMap,
                            user,
                            pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null));
                }).getContent();

                return new PagedResponse<>(pollResponses, polls.getNumber(),
                        polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());

        } catch (Exception ex) {
            logger.error("Error fetching polls created by user: {}", username, ex);
            throw ex; // Adjust this according to your exception handling strategy.
        }
    }

    @Override
    public PagedResponse<CmyPollResponse> getCmyPollsVotedBy(String username, CmyUser currentUser, int page, int size) {
        try {
            logger.info("Fetching polls voted by user: {}, page: {}, size: {}", username, page, size);

            validatePageNumberAndSize(page, size);

                CmyUser user = this.iCmyUserRepository.findByUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

                // Retrieve all pollIds in which the given username has voted
                Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
                Page<Long> userVotedPollIds = this.iCmyVoteRepository.findVotedPollIdsByUserId(user.getId(), pageable);

                if (userVotedPollIds.getNumberOfElements() == 0) {
                    return new PagedResponse<>(Collections.emptyList(), userVotedPollIds.getNumber(),
                            userVotedPollIds.getSize(), userVotedPollIds.getTotalElements(),
                            userVotedPollIds.getTotalPages(), userVotedPollIds.isLast());
                }

                // Retrieve all poll details from the voted pollIds.
                List<Long> pollIds = userVotedPollIds.getContent();

                Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
                List<CmyPoll> polls = this.iCmyPollRepository.findByIdIn(pollIds, sort);

                // Map Polls to PollResponses containing vote counts and poll creator details
                Map<Long, Long> choiceVoteCountMap = getCmyChoiceVoteCountMap(pollIds);
                Map<Long, Long> pollUserVoteMap = getCmyPollUserVoteMap(currentUser, pollIds);
                Map<Long,CmyUser> creatorMap = getCmyPollCreatorMap(polls);

                List<CmyPollResponse> pollResponses = polls.stream().map(poll -> {
                    return ModelMapper.mapCmyPollToCmyPollResponse(poll,
                            choiceVoteCountMap,
                            creatorMap.get(poll.getCreatedBy()),
                            pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null));
                }).collect(Collectors.toList());

                return new PagedResponse<>(pollResponses, userVotedPollIds.getNumber(), userVotedPollIds.getSize(), userVotedPollIds.getTotalElements(), userVotedPollIds.getTotalPages(), userVotedPollIds.isLast());
        } catch (Exception ex) {
            logger.error("Error fetching polls voted by user: {}", username, ex);
            throw ex; // Adjust this according to your exception handling strategy.
        }
    }

    @Override
    public CmyPoll createCmyPoll(CmyPollRequest cmyPollRequest) {
        CmyPoll cmyPoll = new CmyPoll();
        cmyPoll.setQuestion( cmyPollRequest.getQuestion());

        cmyPollRequest.getChoices().forEach(choiceRequest -> {
            cmyPoll.addCmyChoice(new CmyChoice(choiceRequest.getText()));
        });

        Instant now = Instant.now();
        Instant expirationDateTime = now.plus(Duration.ofDays(cmyPollRequest.getPollLength().getDays()))
                .plus(Duration.ofHours(cmyPollRequest.getPollLength().getHours()));

        cmyPoll.setExpirationDateTime(expirationDateTime);

        return iCmyPollRepository.save(cmyPoll);
    }

    @Override
    public CmyPollResponse getCmyPollById(Long pollId, CmyUser currentUser) {
        CmyPoll cmyPoll = iCmyPollRepository.findById(pollId).orElseThrow(
                () -> new ResourceNotFoundException("Cmy Poll", "id", pollId));

        // Retrieve Vote Counts of every choice belonging to the current poll
        List<CmyChoiceVoteCount> votes = iCmyVoteRepository.countByPollIdGroupByChoiceId(pollId);

        Map<Long, Long> choiceVotesMap = votes.stream()
                .collect(Collectors.toMap(CmyChoiceVoteCount::getCmyChoiceId, CmyChoiceVoteCount::getCmyVoteCount));

        // Retrieve poll creator details
        CmyUser creator = iCmyUserRepository.findById(cmyPoll.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", cmyPoll.getCreatedBy()));

        // Retrieve vote done by logged in user
        CmyVote userVote = null;
        if(currentUser != null) {
            userVote = iCmyVoteRepository.findByUserIdAndPollId(currentUser.getId(), pollId);
        }

        return ModelMapper.mapCmyPollToCmyPollResponse(cmyPoll, choiceVotesMap,
                creator, userVote != null ? userVote.getCmyChoice().getId(): null);
    }

    @Override
    public CmyPollResponse castVoteAndGetUpdatedPoll(Long pollId, CmyVoteRequest cmyVoteRequest, CmyUser currentUser) {
        CmyPoll poll = this.iCmyPollRepository.findById(pollId)
                .orElseThrow(() -> new ResourceNotFoundException("Poll", "id", pollId));

        if(poll.getExpirationDateTime().isBefore(Instant.now())) {
            throw new BadRequestException("Sorry! This Poll has already expired");
        }

        CmyUser user = this.iCmyUserRepository.getOne(currentUser.getId());

        CmyChoice selectedChoice = poll.getCmyChoices().stream()
                .filter(choice -> choice.getId().equals(cmyVoteRequest.getCmyChoiceId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cmy Choice", "id", cmyVoteRequest.getCmyChoiceId()));

        CmyVote vote = new CmyVote();
        vote.setCmyPoll(poll);
        vote.setCmyUser(user);
        vote.setCmyChoice(selectedChoice);

        try {
            vote = this.iCmyVoteRepository.save(vote);
        } catch (DataIntegrityViolationException ex) {
            logger.info("Cmy User {} has already voted in Cmy Poll {}", currentUser.getId(), pollId);
            throw new BadRequestException("Sorry! You have already cast your vote in this cmy poll");
        }

        //-- Vote Saved, Return the updated Poll Response now --

        // Retrieve Vote Counts of every choice belonging to the current poll
        List<CmyChoiceVoteCount> votes = this.iCmyVoteRepository.countByPollIdGroupByChoiceId(pollId);

        Map<Long, Long> choiceVotesMap = votes.stream()
                .collect(Collectors.toMap(CmyChoiceVoteCount::getCmyChoiceId, CmyChoiceVoteCount::getCmyVoteCount));

        // Retrieve poll creator details
        CmyUser creator = this.iCmyUserRepository.findById(poll.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", poll.getCreatedBy()));

        return ModelMapper.mapCmyPollToCmyPollResponse(poll, choiceVotesMap, creator, vote.getCmyChoice().getId());

    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    private Map<Long, Long> getCmyChoiceVoteCountMap(List<Long> pollIds) {
        // Retrieve Vote Counts of every Choice belonging to the given pollIds
        List<CmyChoiceVoteCount> votes = iCmyVoteRepository.countByPollIdInGroupByChoiceId(pollIds);

        Map<Long, Long> choiceVotesMap = votes.stream()
                .collect(Collectors.toMap(CmyChoiceVoteCount::getCmyChoiceId, CmyChoiceVoteCount::getCmyVoteCount));

        return choiceVotesMap;
    }

    private Map<Long, Long> getCmyPollUserVoteMap(CmyUser currentUser, List<Long> pollIds) {
        // Retrieve Votes done by the logged in user to the given pollIds
        Map<Long, Long> pollUserVoteMap = null;
        if(currentUser != null) {
            List<CmyVote> userVotes = iCmyVoteRepository.findByUserIdAndPollIdIn(currentUser.getId(), pollIds);

            pollUserVoteMap = userVotes.stream()
                    .collect(Collectors.toMap(vote -> vote.getCmyPoll().getId(), vote -> vote.getCmyChoice().getId()));
        }
        return pollUserVoteMap;
    }

    Map<Long, CmyUser> getCmyPollCreatorMap(List<CmyPoll> polls) {

        try {
            logger.info("Fetching poll creators for {} polls", polls.size());

            // Get Poll Creator details of the given list of polls
            List<Long> creatorIds = polls.stream()
                    .map(CmyPoll::getCreatedBy)
                    .distinct()
                    .collect(Collectors.toList());

            List<CmyUser> creators = iCmyUserRepository.findByIdIn(creatorIds);
            Map<Long, CmyUser> creatorMap = creators.stream()
                    .collect(Collectors.toMap(CmyUser::getId, Function.identity()));

        return creatorMap;
        } catch (Exception ex) {
            logger.error("Error fetching poll creators", ex);
            throw ex; // Adjust this according to your exception handling strategy.
        }
    }
}
