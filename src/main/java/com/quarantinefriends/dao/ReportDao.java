package com.quarantinefriends.dao;

import com.quarantinefriends.dto.ReportDTO;
import com.quarantinefriends.entity.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportDao {

    public static ReportDTO mapToDTO(Report report) {
        ReportDTO reportDTO = new ReportDTO();
        if(report != null) {
            reportDTO.setId(report.getId());
            reportDTO.setFromUser(UserDao.mapToDTO(report.getFromUser()));
            reportDTO.setToUser(UserDao.mapToDTO(report.getToUser()));
            reportDTO.setDate(report.getDate());
        }
        return reportDTO;
    }

    public static Report mapToEntity(ReportDTO reportDTO) {
        Report report = new Report();
        if(reportDTO != null) {
            report.setId(reportDTO.getId());
            report.setFromUser(UserDao.mapToEntity(reportDTO.getFromUser()));
            report.setToUser(UserDao.mapToEntity(reportDTO.getToUser()));
            report.setDate(reportDTO.getDate());
        }
        return report;
    }

}
