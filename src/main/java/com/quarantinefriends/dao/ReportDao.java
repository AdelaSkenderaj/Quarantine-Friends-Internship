package com.quarantinefriends.dao;

import com.quarantinefriends.dto.ReportDTO;
import com.quarantinefriends.entity.Report;
import com.quarantinefriends.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportDao {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportDao(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    public static ReportDTO mapToDTO(Report report) {
        ReportDTO reportDTO = new ReportDTO();
        if(report != null) {
            reportDTO.setId(report.getId());
            reportDTO.setUser(UserDao.mapToDTO(report.getUser()));
            reportDTO.setDate(report.getDate());
        }
        return reportDTO;
    }

    public static Report mapToEntity(ReportDTO reportDTO) {
        Report report = new Report();
        if(reportDTO != null) {
            report.setId(reportDTO.getId());
            report.setUser(UserDao.mapToEntity(reportDTO.getUser()));
            report.setDate(reportDTO.getDate());
        }
        return report;
    }

    public void save(Report report) {
        reportRepository.save(report);
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }
}
