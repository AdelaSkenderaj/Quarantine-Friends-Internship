package com.quarantinefriends.controller;

import com.quarantinefriends.dto.PreferenceDTO;
import com.quarantinefriends.service.PreferenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class PreferenceController {

    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping("/preferences")
    public List<PreferenceDTO> getPreferences() {
        return preferenceService.getPreferences();
    }

    @PostMapping("/preferences")
    public void addPreference(@RequestBody PreferenceDTO preferenceDTO) {
        this.preferenceService.addPreference(preferenceDTO);
    }

    @DeleteMapping("preferences/{preferenceId}")
    public void deletePreference(@PathVariable Long preferenceId) {
        this.preferenceService.deletePreference(preferenceId);
    }

}
