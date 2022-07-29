package com.quarantinefriends.dao;

import com.quarantinefriends.dto.ReportDTO;
import com.quarantinefriends.entity.Report;
import com.quarantinefriends.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public void save(ReportDTO reportDTO) {
        reportRepository.save(mapToEntity(reportDTO));
    }

    public List<ReportDTO> findReportsForNonTerminatedAccounts() {
        return reportRepository.findReportsForNonTerminatedAccounts().stream().map(ReportDao::mapToDTO).collect(Collectors.toList());
    }
}
