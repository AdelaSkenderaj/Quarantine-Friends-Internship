package com.quarantinefriends.controller;

import com.quarantinefriends.dto.HobbyDTO;
import com.quarantinefriends.service.HobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class HobbyController {

    private HobbyService hobbyService;

    @Autowired
    public HobbyController(HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    @GetMapping("/hobbies")
    public List<HobbyDTO> getHobbies() {
        return hobbyService.getHobbies();
    }

}
