package com.quarantinefriends.service;

import com.quarantinefriends.dao.MatchRequestDao;
import com.quarantinefriends.dao.UserDao;
import com.quarantinefriends.dto.MatchRequestDTO;
import com.quarantinefriends.dto.UserDTO;
import com.quarantinefriends.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new UserNotFoundException("User does not exist");
        }

        UserDTO toUser = userDao.findById(toUserId);
        if(toUser == null) {
            throw new UserNotFoundException("User does not exist");
        }

        matchRequestDTO.setFromUser(fromUser);
        matchRequestDTO.setToUser(toUser);
        matchRequestDao.save(matchRequestDTO);
    }
}
