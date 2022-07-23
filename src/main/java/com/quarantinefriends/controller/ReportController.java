package com.quarantinefriends.controller;

import com.quarantinefriends.dto.ReportDTO;
import com.quarantinefriends.exception.UserNotFoundException;
import com.quarantinefriends.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/reports/{userId}")
    public void addReport(@PathVariable Long userId) throws UserNotFoundException {
        reportService.addReport(userId);
    }

    @GetMapping("/reports")
    public List<ReportDTO> getAllReports() {
        return reportService.getAllReports();
    }
}
