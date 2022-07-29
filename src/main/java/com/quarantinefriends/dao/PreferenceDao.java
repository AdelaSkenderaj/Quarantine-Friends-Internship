package com.quarantinefriends.dao;

import com.quarantinefriends.dto.PreferenceDTO;
import com.quarantinefriends.entity.Preference;
import com.quarantinefriends.repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PreferenceDao {

    private final PreferenceRepository preferenceRepository;

    @Autowired
    public PreferenceDao(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public static PreferenceDTO mapToDTO(Preference preference) {
        PreferenceDTO preferenceDTO = new PreferenceDTO();
        if(preference != null) {
            preferenceDTO.setId(preference.getId());
            preferenceDTO.setName(preference.getName());
        }
        return preferenceDTO;
    }

    public static Preference mapToEntity(PreferenceDTO preferenceDTO) {
        Preference preference = new Preference();
        if(preferenceDTO != null) {
            preference.setId(preferenceDTO.getId());
            preference.setName(preferenceDTO.getName());
        }
        return preference;
    }

    public List<PreferenceDTO> findAll() {
        return preferenceRepository.findAll().stream().map(PreferenceDao::mapToDTO).collect(Collectors.toList());
    }

    public void addPreference(PreferenceDTO preferenceDTO) {
        this.preferenceRepository.save(mapToEntity(preferenceDTO));
    }

    public void deletePreference(Long preferenceId) {
        this.preferenceRepository.deleteFromUsers(preferenceId);
        this.preferenceRepository.deleteById(preferenceId);
    }
}
