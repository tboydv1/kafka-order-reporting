package com.bankwithmint.reporting.repository;

import com.bankwithmint.reporting.data.ReportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 14/07/2021
 * inside the package - com.bankwithmint.reporting.data
 */
public interface ReportDataRepository extends JpaRepository<ReportData, Long> {

    @Query("select a from ReportData a where a.dateCreated >= :fromDate and a.dateCreated <= :toDate")
    List<ReportData> findByDateCreatedRange(LocalDate fromDate, LocalDate toDate);
}
