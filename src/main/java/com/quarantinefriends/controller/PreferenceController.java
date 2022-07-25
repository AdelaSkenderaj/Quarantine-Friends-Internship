package com.quarantinefriends.controller;

import com.quarantinefriends.dto.PreferenceDTO;
import com.quarantinefriends.service.PreferenceService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class PreferenceController {

    private PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping("/preferences")
    public List<PreferenceDTO> getPreferences() {
        return preferenceService.getPreferences();
    }
}
