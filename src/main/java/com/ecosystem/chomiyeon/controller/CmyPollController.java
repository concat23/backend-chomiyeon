package com.ecosystem.chomiyeon.controller;

import com.ecosystem.chomiyeon.repository.ICmyPollRepository;
import com.ecosystem.chomiyeon.repository.ICmyUserRepository;
import com.ecosystem.chomiyeon.repository.ICmyVoteRepository;
import com.ecosystem.chomiyeon.service.ICmyPollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cmy-polls")
public class CmyPollController {
    @Autowired
    private ICmyPollRepository iCmyPollRepository;

    @Autowired
    private ICmyVoteRepository iCmyVoteRepository;

    @Autowired
    private ICmyUserRepository iCmyUserRepository;

    @Autowired
    private ICmyPollService iCmyPollService;




}
