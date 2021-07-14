package com.bankwithmint.reporting.data;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 14/07/2021
 * inside the package - com.bankwithmint.reporting.data
 */

@Data
public class ReportRequestDto {

    @NotNull(message = "Starting date cannot be empty")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate from;
    @NotNull(message = "Ending date cannot be empty")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate to;

}
