package com.example.SpringProject.repository;

import com.example.SpringProject.model.CardPrintRequest;

import java.util.List;

public interface CustomCardPrintRequestRepository {
    List<String> findAllBranchCodeByIpAddress(String ipAddress);
}
