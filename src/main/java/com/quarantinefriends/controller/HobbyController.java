package com.quarantinefriends.controller;

import com.quarantinefriends.dto.HobbyDTO;
import com.quarantinefriends.service.HobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class HobbyController {

    private final HobbyService hobbyService;

    @Autowired
    public HobbyController(HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    @GetMapping("/hobbies")
    public List<HobbyDTO> getHobbies() {
        return hobbyService.getHobbies();
    }

    @PostMapping("/hobbies")
    public void addHobby(@RequestBody HobbyDTO hobbyDTO) {
        this.hobbyService.addHobby(hobbyDTO);
    }

    @DeleteMapping("/hobbies/{hobbyId}")
    public void deleteHobby(@PathVariable Long hobbyId) {
        this.hobbyService.deleteHobby(hobbyId);
    }
}
