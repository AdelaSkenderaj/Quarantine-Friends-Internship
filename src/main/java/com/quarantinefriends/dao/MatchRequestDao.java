package com.quarantinefriends.dao;

import com.quarantinefriends.dto.MatchRequestDTO;
import com.quarantinefriends.entity.MatchRequest;
import com.quarantinefriends.repository.MatchRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MatchRequestDao {

    private final MatchRequestRepository matchRequestRepository;

    @Autowired
    public MatchRequestDao(MatchRequestRepository matchRequestRepository) {
        this.matchRequestRepository = matchRequestRepository;
    }

    public static MatchRequestDTO mapToDTO(MatchRequest matchRequest) {
        MatchRequestDTO matchRequestDTO = new MatchRequestDTO();
        if(matchRequest != null) {
            matchRequestDTO.setId(matchRequest.getId());
            matchRequestDTO.setFromUser(UserDao.mapToDTO(matchRequest.getFromUser()));
            matchRequestDTO.setToUser(UserDao.mapToDTO(matchRequest.getToUser()));
        }
        return matchRequestDTO;
    }

    public static MatchRequest mapToEntity(MatchRequestDTO matchRequestDTO) {
        MatchRequest matchRequest = new MatchRequest();
        if(matchRequestDTO != null) {
            matchRequest.setId(matchRequestDTO.getId());
            matchRequest.setFromUser(UserDao.mapToEntity(matchRequestDTO.getFromUser()));
            matchRequest.setToUser(UserDao.mapToEntity(matchRequestDTO.getToUser()));
        }
        return  matchRequest;
    }

    public void save(MatchRequestDTO matchRequestDTO) {
        matchRequestRepository.save(mapToEntity(matchRequestDTO));
    }

    public MatchRequestDTO findById(Long matchRequestId) {
        Optional<MatchRequest> matchRequest = matchRequestRepository.findById(matchRequestId);
        return matchRequest.map(MatchRequestDao :: mapToDTO).orElse(null);
    }

    public void deleteById(Long matchRequestId) {
        matchRequestRepository.deleteById(matchRequestId);
    }

    public List<MatchRequestDTO> getRequestsForUser(Long userId) {
        return matchRequestRepository.findMatchRequestsForUser(userId).stream().map(MatchRequestDao::mapToDTO).collect(Collectors.toList());
    }

    public List<MatchRequestDTO> getRequestsFromUser(Long userId) {
        return matchRequestRepository.findMatchRequestsFromUser(userId).stream().map(MatchRequestDao::mapToDTO).collect(Collectors.toList());
    }
}
