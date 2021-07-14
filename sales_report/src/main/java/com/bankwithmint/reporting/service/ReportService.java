package com.bankwithmint.reporting.service;

import com.bankwithmint.reporting.data.ApiResponse;
import com.bankwithmint.reporting.data.ReportData;
import com.bankwithmint.reporting.data.ReportRequestDto;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 14/07/2021
 * inside the package - com.bankwithmint.reporting.service
 */
public interface ReportService {

    Map<LocalDate, ApiResponse> getAllReportDataByDate();
    Map<LocalDate, ApiResponse> getAllReportDataByDateRange(ReportRequestDto reportRequestDto);

}
