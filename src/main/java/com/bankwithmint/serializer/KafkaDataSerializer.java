package com.bankwithmint.serializer;

import com.bankwithmint.reporting.data.ReportData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author oluwatobi
 * @version 1.0
 * @date on 14/07/2021
 * inside the package - com.bankwithmint.sales.serializer
 */


public class KafkaDataSerializer implements Serializer<ReportData> {
    @Override
    public byte[] serialize(String s, ReportData reportData) {

        ObjectMapper mapper = new ObjectMapper();
        byte[] reportAsBytes = null;

        try{
            if(reportData != null){
                reportAsBytes = mapper.writeValueAsBytes(reportData);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return reportAsBytes;
    }
}
