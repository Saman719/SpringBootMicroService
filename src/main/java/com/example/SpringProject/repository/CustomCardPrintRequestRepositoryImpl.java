package com.example.SpringProject.repository;

import com.example.SpringProject.model.CardPrintRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class CustomCardPrintRequestRepositoryImpl implements CustomCardPrintRequestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List findAllBranchCodeByIpAddress(String ipAddress) {
        List requests = entityManager.createQuery("select c.cardPrintRequestPK.branchCode from CardPrintRequest c where c.cardPrintRequestPK.ipAddress = :ipaddress")
                .setParameter("ipaddress",ipAddress)
                .getResultList();
        return requests;
    }
}
