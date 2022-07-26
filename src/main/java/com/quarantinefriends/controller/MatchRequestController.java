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

    @PostMapping("/match-request")
    public void addMatchRequest(@RequestBody MatchRequestDTO matchRequestDTO) {
        matchRequestService.addMatchRequest(matchRequestDTO);
    }

    @DeleteMapping("/match-request-remove/{matchRequestId}")
    public void deleteMatchRequest(@PathVariable Long matchRequestId) throws MatchRequestNotFoundException {
        matchRequestService.deleteMatchRequest(matchRequestId);
    }

    @PutMapping("/match-request-accept/{matchRequestId}")
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
