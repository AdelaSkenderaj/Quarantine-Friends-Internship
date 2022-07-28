package com.quarantinefriends.controller;

import com.quarantinefriends.dao.StatisticsDao;
import com.quarantinefriends.dto.ReportDTO;
import com.quarantinefriends.exception.UserNotFoundException;
import com.quarantinefriends.service.ReportService;
import com.quarantinefriends.service.StatisticsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class ReportController {

    private final ReportService reportService;
    private final StatisticsService statisticsService;

    public ReportController(ReportService reportService, StatisticsService statisticsService) {
        this.reportService = reportService;
        this.statisticsService = statisticsService;
    }

    @PostMapping("/reports")
    public void addReport(@RequestBody ReportDTO reportDTO) throws UserNotFoundException {
        reportService.addReport(reportDTO);
    }

    @GetMapping("/reports")
    public List<ReportDTO> getAllReports() {
        return reportService.getAllReports();
    }

}
