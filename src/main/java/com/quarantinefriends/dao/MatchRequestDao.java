package com.quarantinefriends.dao;

import com.quarantinefriends.dto.MatchRequestDTO;
import com.quarantinefriends.entity.MatchRequest;
import org.springframework.stereotype.Component;

@Component
public class MatchRequestDao {

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
}
