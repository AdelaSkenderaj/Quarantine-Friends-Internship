package com.quarantinefriends.controller;

import com.quarantinefriends.dto.ReportDTO;
import com.quarantinefriends.exception.UserNotFoundException;
import com.quarantinefriends.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
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
