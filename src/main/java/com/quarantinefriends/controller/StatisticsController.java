package com.quarantinefriends.controller;

import com.quarantinefriends.dto.StatisticsDTO;
import com.quarantinefriends.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:4200")
public class StatisticsController {

    private StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics")
    public StatisticsDTO getStatistics() {
        return this.statisticsService.getStatistics();
    }
}
