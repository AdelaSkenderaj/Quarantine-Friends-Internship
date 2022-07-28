package com.quarantinefriends.dao;

import com.quarantinefriends.dto.HobbyDTO;
import com.quarantinefriends.dto.HobbyStatistics;
import com.quarantinefriends.dto.PreferenceDTO;
import com.quarantinefriends.dto.PreferenceStatistics;
import com.quarantinefriends.repository.HobbyRepository;
import com.quarantinefriends.repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatisticsDao {

    private HobbyRepository hobbyRepository;

    private PreferenceRepository preferenceRepository;

    @Autowired
    public StatisticsDao(HobbyRepository hobbyRepository, PreferenceRepository preferenceRepository) {
        this.hobbyRepository = hobbyRepository;
        this.preferenceRepository = preferenceRepository;
    }

    public List<HobbyStatistics> getHobbiesStatistics() {
        List<HobbyDTO> hobbies = hobbyRepository.findAll().stream().map(HobbyDao::mapToDTO).collect(Collectors.toList());
        List<Long> numbers = hobbyRepository.findHobbyStatistics();
        List<HobbyStatistics> hobbyStatistics = new ArrayList<>();

        if(!hobbies.isEmpty() && !numbers.isEmpty()) {
            for(int i = 0; i < hobbies.size(); i++) {
                HobbyStatistics statistics = new HobbyStatistics();
                statistics.setHobby(hobbies.get(i));
                statistics.setTimesChosen(numbers.get(i));
                hobbyStatistics.add(statistics);
            }
        }
        return hobbyStatistics;
    }

    public List<PreferenceStatistics> getPreferenceStatistics() {
        List<PreferenceDTO> preferences = preferenceRepository.findAll().stream().map(PreferenceDao::mapToDTO).collect(Collectors.toList());
        List<Long> numbers = preferenceRepository.findPreferenceStatistics();
        List<PreferenceStatistics> preferenceStatistics =  new ArrayList<>();

        if(!preferences.isEmpty() && !numbers.isEmpty()) {
            for(int i = 0; i < preferences.size(); i++) {
                PreferenceStatistics statistics = new PreferenceStatistics();
                statistics.setPreference(preferences.get(i));
                statistics.setTimesChosen(numbers.get(i));
                preferenceStatistics.add(statistics);
            }
        }
        return preferenceStatistics;
    }

}
