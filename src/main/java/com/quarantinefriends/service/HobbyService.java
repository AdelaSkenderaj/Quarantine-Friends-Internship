package com.quarantinefriends.service;

import com.quarantinefriends.dao.HobbyDao;
import com.quarantinefriends.dto.HobbyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbyService {

    private HobbyDao hobbyDao;

    @Autowired
    public HobbyService(HobbyDao hobbyDao) {
        this.hobbyDao = hobbyDao;
    }
    public List<HobbyDTO> getHobbies() {
        return hobbyDao.findAll();
    }
}
