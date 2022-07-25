package com.quarantinefriends.controller;

import com.quarantinefriends.dao.MatchRequestDao;
import com.quarantinefriends.dto.MatchRequestDTO;
import com.quarantinefriends.exception.MatchRequestNotFoundException;
import com.quarantinefriends.exception.UserNotFoundException;
import com.quarantinefriends.service.MatchRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin("http://localhost:4200")
public class MatchRequestController {

    private final MatchRequestService matchRequestService;

    public MatchRequestController(MatchRequestService matchRequestService) {
        this.matchRequestService = matchRequestService;
    }

    @PostMapping("/match-request/{fromUserId}/{toUserId}")
    public void addMatchRequest(@PathVariable Long fromUserId, @PathVariable Long toUserId) throws UserNotFoundException {
        matchRequestService.addMatchRequest(fromUserId, toUserId);
    }

    @DeleteMapping("/match-request/{matchRequestId}")
    public void deleteMatchRequest(@PathVariable Long matchRequestId) throws MatchRequestNotFoundException {
        matchRequestService.deleteMatchRequest(matchRequestId);
    }

    @PutMapping("/match-request/{matchRequestId}")
    public void acceptMatchRequest(@PathVariable Long matchRequestId) throws MatchRequestNotFoundException, UserNotFoundException {
        matchRequestService.acceptMatchRequest(matchRequestId);
    }

    @GetMapping("/match-request-to/{userId}")
    public List<MatchRequestDTO> getRequestsForUser(@PathVariable Long userId) {
        return matchRequestService.getRequestsForUser(userId);
    }

    @GetMapping("/match-request-from/{userId}")
    public List<MatchRequestDTO> getRequestsFromUser(@PathVariable Long userId){
        return matchRequestService.getRequestsFromUser(userId);
    }


}
