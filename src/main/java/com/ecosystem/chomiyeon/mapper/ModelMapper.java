package com.ecosystem.chomiyeon.mapper;

import com.ecosystem.chomiyeon.entity.CmyPoll;
import com.ecosystem.chomiyeon.entity.CmyUser;
import com.ecosystem.chomiyeon.payload.CmyChoiceResponse;
import com.ecosystem.chomiyeon.payload.CmyPollResponse;
import com.ecosystem.chomiyeon.payload.CmyUserSummary;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelMapper {
    public static CmyPollResponse mapCmyPollToCmyPollResponse(CmyPoll cmyPoll, Map<Long, Long> cmyChoiceVotesMap, CmyUser creator, Long userVote){
        CmyPollResponse cmyPollResponse = new CmyPollResponse();
        cmyPollResponse.setId(cmyPoll.getId());
        cmyPollResponse.setQuestion(cmyPoll.getQuestion());
        cmyPollResponse.setCreationDateTime(cmyPoll.getCreatedAt());
        cmyPollResponse.setExpirationDateTime(cmyPoll.getExpirationDateTime());
        Instant now = Instant.now();
        cmyPollResponse.setExpired(cmyPoll.getExpirationDateTime().isBefore(now));

        List<CmyChoiceResponse> cmyChoiceResponses = cmyPoll.getCmyChoices().stream().map(choice -> {
            CmyChoiceResponse choiceResponse = new CmyChoiceResponse();
            choiceResponse.setId(choice.getId());
            choiceResponse.setText(choice.getText());

            if(cmyChoiceVotesMap.containsKey(choice.getId())) {
                choiceResponse.setVoteCount(cmyChoiceVotesMap.get(choice.getId()));
            } else {
                choiceResponse.setVoteCount(0);
            }
            return choiceResponse;
        }).collect(Collectors.toList());

        cmyPollResponse.setChoices(cmyChoiceResponses);
        CmyUserSummary creatorSummary = new CmyUserSummary(creator.getId(), creator.getUsername(), creator.getLastName() + creator.getFirstName());
        cmyPollResponse.setCreatedBy(creatorSummary);

        if(userVote != null) {
            cmyPollResponse.setSelectedChoice(userVote);
        }

        long totalVotes =  cmyPollResponse.getChoices().stream().mapToLong(CmyChoiceResponse::getVoteCount).sum();
        cmyPollResponse.setTotalVotes(totalVotes);

        return cmyPollResponse;
    }
}
