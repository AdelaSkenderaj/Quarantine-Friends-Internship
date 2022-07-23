package com.quarantinefriends.service;

import com.quarantinefriends.dao.MatchRequestDao;
import com.quarantinefriends.dao.UserDao;
import com.quarantinefriends.dto.MatchRequestDTO;
import com.quarantinefriends.dto.UserDTO;
import com.quarantinefriends.entity.MatchRequest;
import com.quarantinefriends.exception.MatchRequestNotFoundException;
import com.quarantinefriends.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchRequestService {

    private MatchRequestDao matchRequestDao;
    private UserDao userDao;

    @Autowired
    public MatchRequestService(MatchRequestDao matchRequestDao, UserDao userDao) {
        this.matchRequestDao = matchRequestDao;
        this.userDao = userDao;
    }

    public void addMatchRequest(Long fromUserId, Long toUserId) throws UserNotFoundException {

        MatchRequestDTO matchRequestDTO = new MatchRequestDTO();
        UserDTO fromUser = userDao.findById(fromUserId);
        if(fromUser == null) {
            throw new UserNotFoundException();
        }

        UserDTO toUser = userDao.findById(toUserId);
        if(toUser == null) {
            throw new UserNotFoundException();
        }

        matchRequestDTO.setFromUser(fromUser);
        matchRequestDTO.setToUser(toUser);
        matchRequestDao.save(matchRequestDTO);
    }

    public void deleteMatchRequest(Long matchRequestId) throws MatchRequestNotFoundException {
        MatchRequestDTO matchRequestDTO = matchRequestDao.findById(matchRequestId);
        if(matchRequestDTO == null) {
            throw new MatchRequestNotFoundException();
        }

        matchRequestDao.deleteById(matchRequestId);
    }

    public void acceptMatchRequest(Long matchRequestId) throws MatchRequestNotFoundException, UserNotFoundException {
        MatchRequestDTO matchRequestDTO = matchRequestDao.findById(matchRequestId);
        if(matchRequestDTO == null) {
            throw new MatchRequestNotFoundException();
        }

        UserDTO fromUser = matchRequestDTO.getFromUser();
        UserDTO toUser = matchRequestDTO.getToUser();

        userDao.addFriend(fromUser.getId(), toUser.getId());
        matchRequestDao.deleteById(matchRequestId);
    }

    public List<MatchRequestDTO> getRequestsForUser(Long userId) {
        return matchRequestDao.getRequestsForUser(userId);
    }

    public List<MatchRequestDTO> getRequestsFromUser(Long userId) {
        return matchRequestDao.getRequestsFromUser(userId);
    }
}
