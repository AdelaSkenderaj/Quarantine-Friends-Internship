package com.quarantinefriends.service;

import com.quarantinefriends.dao.MessageDao;
import com.quarantinefriends.dao.StatisticsDao;
import com.quarantinefriends.dao.UserDao;
import com.quarantinefriends.dto.StatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final StatisticsDao statisticsDao;

    private final UserDao userDao;

    private final MessageDao messageDao;

    @Autowired
    public StatisticsService(StatisticsDao statisticsDao, UserDao userDao, MessageDao messageDao) {
        this.statisticsDao = statisticsDao;
        this.userDao = userDao;
        this.messageDao = messageDao;

    }

    public StatisticsDTO getStatistics() {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setHobbyStatistics(statisticsDao.getHobbiesStatistics());
        statisticsDTO.setPreferenceStatistics(statisticsDao.getPreferenceStatistics());
        statisticsDTO.setNrUsers(userDao.getNrUsers());
        statisticsDTO.setNrBannedUsers(userDao.getNrBannedUsers());
        statisticsDTO.setNrExchangedMessages(messageDao.getNrExchangedMessages());
        statisticsDTO.setNrMatches(userDao.getNrFriendships() / 2);
        return statisticsDTO;
    }
}
