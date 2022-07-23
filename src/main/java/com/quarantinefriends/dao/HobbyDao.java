package com.quarantinefriends.dao;

import com.quarantinefriends.dto.HobbyDTO;
import com.quarantinefriends.entity.Hobby;
import org.springframework.stereotype.Component;

@Component
public class HobbyDao {


    public static HobbyDTO mapToDTO(Hobby hobby) {
        HobbyDTO hobbyDTO = new HobbyDTO();
        if(hobby != null) {
            hobbyDTO.setId(hobby.getId());
            hobbyDTO.setName(hobby.getName());
        }
        return hobbyDTO;
    }

    public static Hobby mapToEntity(HobbyDTO hobbyDTO) {
        Hobby hobby = new Hobby();
        if(hobbyDTO != null) {
            hobby.setId(hobbyDTO.getId());
            hobby.setName(hobbyDTO.getName());
        }
        return hobby;
    }
}
