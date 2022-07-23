package com.quarantinefriends.dao;

import com.quarantinefriends.dto.PreferenceDTO;
import com.quarantinefriends.entity.Preference;
import org.springframework.stereotype.Component;

@Component
public class PreferenceDao {

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
}
