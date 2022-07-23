package com.quarantinefriends.controller;

import com.quarantinefriends.service.ReportService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


}
