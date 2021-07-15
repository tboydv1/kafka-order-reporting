package com.bankwithmint.reporting.data;

import com.bankwithmint.reporting.repository.ReportDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 14/07/2021
 * inside the package - com.bankwithmint.reporting.data
 */

@SpringBootTest
@Sql(scripts = {"classpath:/db/test-data.sql"})
@Slf4j
class ReportDataRepositoryTest {

    @Autowired
    ReportDataRepository reportDataRepository;

    @BeforeEach
    void setUp() {
    }

    /**
     *
     */
    @Test
    void findAllByDateCreatedRangeTest(){

        LocalDate from = LocalDate.of(2021, 7, 10);
        LocalDate to = LocalDate.of(2021, 9, 15);

        List<ReportData> reportDataList = reportDataRepository.findByDateCreatedRange(from, to);
        reportDataList.forEach(reportData -> {
            assertThat(reportData.getDateCreated()).isAfter(from);
            assertThat(reportData.getDateCreated()).isBefore(to);
        });

        log.info("Report data retrieved --> {}", reportDataList);

        assertThat(reportDataList.size()).isEqualTo(6);


    }

    @Test
    void findAllReportDataObjectsTest(){
        List<ReportData> reportDataList = reportDataRepository.findAll();
        assertNotNull(reportDataList);
        assertThat(reportDataList.size()).isEqualTo(15);
    }
}