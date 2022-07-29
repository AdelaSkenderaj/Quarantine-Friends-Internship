package com.quarantinefriends.dao;

import com.quarantinefriends.dto.HobbyDTO;
import com.quarantinefriends.entity.Hobby;
import com.quarantinefriends.repository.HobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HobbyDao {

    private final HobbyRepository hobbyRepository;

    @Autowired
    public HobbyDao(HobbyRepository hobbyRepository){
        this.hobbyRepository = hobbyRepository;
    }

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

    public List<HobbyDTO> findAll() {
        return this.hobbyRepository.findAll().stream().map(HobbyDao::mapToDTO).collect(Collectors.toList());
    }

    public void addHobby(HobbyDTO hobbyDTO) {
        this.hobbyRepository.save(mapToEntity(hobbyDTO));
    }

    public void deleteHobby(Long hobbyId) {
        this.hobbyRepository.deleteById(hobbyId);
    }
}
