package com.quarantinefriends.service;

import com.quarantinefriends.dao.ReportDao;
import com.quarantinefriends.dao.UserDao;
import com.quarantinefriends.dto.ReportDTO;
import com.quarantinefriends.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportDao reportDao;
    private final UserDao userDao;

    @Autowired
    public ReportService(ReportDao reportDao, UserDao userDao) {
        this.reportDao = reportDao;
        this.userDao = userDao;
    }

    public void addReport(Long userId) throws UserNotFoundException {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setDate(LocalDateTime.now());
        reportDTO.setUser(userDao.findById(userId));
        reportDao.save(ReportDao.mapToEntity(reportDTO));
    }

    public List<ReportDTO> getAllReports() {
        return reportDao.findAll().stream().map(ReportDao :: mapToDTO).collect(Collectors.toList());
    }
}
