package com.quarantinefriends.controller;

import com.quarantinefriends.dao.MatchRequestDao;
import com.quarantinefriends.exception.UserNotFoundException;
import com.quarantinefriends.service.MatchRequestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MatchRequestController {

    private final MatchRequestService matchRequestService;

    public MatchRequestController(MatchRequestService matchRequestService) {
        this.matchRequestService = matchRequestService;
    }

    @PostMapping("/match-request/{fromUserId}/{toUserId}")
    public void addMatchRequest(@PathVariable Long fromUserId, @PathVariable Long toUserId) throws UserNotFoundException {
        matchRequestService.addMatchRequest(fromUserId, toUserId);
    }

}
