package com.bankwithmint.reporting.service;

import com.bankwithmint.reporting.data.ApiResponse;
import com.bankwithmint.reporting.data.ReportData;
import com.bankwithmint.reporting.data.ReportRequestDto;
import com.bankwithmint.reporting.repository.ReportDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 14/07/2021
 * inside the package - com.bankwithmint.reporting.service
 */

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    ReportDataRepository reportDataRepository;


    @Override
    public Map<LocalDate, ApiResponse> getAllReportDataByDate() {
        List<ReportData> reports = reportDataRepository.findAll();

        return getApiResponseMap(reports);
    }

    @Override
    public Map<LocalDate, ApiResponse> getAllReportDataByDateRange(ReportRequestDto reportRequestDto) {
        List<ReportData> reportData = reportDataRepository.
                findByDateCreatedRange(reportRequestDto.getFrom(), reportRequestDto.getTo());

        return getApiResponseMap(reportData);
    }

    private Map<LocalDate, ApiResponse> getApiResponseMap(List<ReportData> reports) {
        Map<LocalDate, List<ReportData>> result =
                reports.stream()
                        .collect(Collectors.groupingBy(ReportData::getDateCreated,
                                Collectors.toList()));


        Map<LocalDate, ApiResponse> finalResult = new HashMap<>();

        for (Map.Entry<LocalDate, List<ReportData>> entry : result.entrySet()) {


            int totalOrders = result.get(entry.getKey()).size();
            Double totalOrderAmount = result.get(entry.getKey()).stream()
                    .map(ReportData::getTotalPrice)
                    .reduce(0.0, Double::sum);


            finalResult.put(entry.getKey(), new ApiResponse(totalOrders, totalOrderAmount));

        }
        return finalResult;
    }

}
