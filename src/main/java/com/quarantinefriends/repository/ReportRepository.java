package com.quarantinefriends.repository;

import com.quarantinefriends.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query(value="select report.id, report.date, report.user_id from report inner join user on user.id=report.user_id where account_terminated=false", nativeQuery = true)
    List<Report> findReportsForNonTerminatedAccounts();
}
