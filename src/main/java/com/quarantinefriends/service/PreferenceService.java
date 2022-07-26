package com.quarantinefriends.service;

import com.quarantinefriends.dao.PreferenceDao;
import com.quarantinefriends.dto.PreferenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenceService {


    private final PreferenceDao preferenceDao;

    @Autowired
    public PreferenceService(PreferenceDao preferenceDao) {
        this.preferenceDao = preferenceDao;
    }
    public List<PreferenceDTO> getPreferences() {
        return preferenceDao.findAll();
    }

    public void addPreference(PreferenceDTO preferenceDTO) {
        this.preferenceDao.addPreference(preferenceDTO);
    }

    public void deletePreference(Long preferenceId) {
        this.preferenceDao.deletePreference(preferenceId);
    }
}
