package com.flowcode.fraud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FraudCheckService {

    private FraudCheckHistoryRepository historyRepository;

    public boolean isFraudulentCustomer(Integer customerId){
        historyRepository.save(FraudCheckHistory.builder()
                        .isFraudster(false)
                        .customerId(customerId)
                        .createdAt(LocalDateTime.now())
                        .build());
        return false;
    }
}
