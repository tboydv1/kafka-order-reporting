package com.bankwithmint.reporting.api;

import com.bankwithmint.reporting.data.ApiResponse;
import com.bankwithmint.reporting.data.ReportRequestDto;
import com.bankwithmint.reporting.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Map;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 13/07/2021
 * inside the package - com.bankwithmint.reporting.api
 */

@RestController
@Slf4j
public class ReportController {

   @Autowired
   ReportService reportService;


    @GetMapping("/")
    public ResponseEntity<?> getAllReport(){

        Map<LocalDate, ApiResponse> finalResult = reportService.getAllReportDataByDate();
        return ResponseEntity.ok().body(finalResult);
    }


    @GetMapping("/date-range")
    public ResponseEntity<?> getReportByDateRange(@Valid @RequestBody ReportRequestDto reportRequestDto){


        Map<LocalDate, ApiResponse> finalResult = reportService.getAllReportDataByDateRange(reportRequestDto);
        return ResponseEntity.ok().body(finalResult);
    }





}
