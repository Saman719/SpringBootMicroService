package com.example.SpringProject.service;

import com.example.SpringProject.exception.DuplicatePrimaryKeyException;
import com.example.SpringProject.exception.RestControllerException;
import com.example.SpringProject.model.CardPrintRequest;
import com.example.SpringProject.repository.CardPrintRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CardPrintRequestService {

    @Autowired
    CardPrintRequestRepository repository;

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
        public List<CardPrintRequest> getCMSPrintRequestsByIpAddress(String ipAddress) {
        return repository.findCardPrintRequestsByCardPrintRequestPK_IpAddress(ipAddress);
    }

    @Transactional
    public void deleteAllRequestsByCardPAN(String cardPAN) {
        repository.deleteAllByCardPAN(cardPAN);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public CardPrintRequest insertNewCardPrintRequest(CardPrintRequest cardPrintRequest) throws Exception {
        Optional<CardPrintRequest> cardPrintRequestOptional = repository.findById(cardPrintRequest.getCardPrintRequestPK());
        if(cardPrintRequestOptional.isPresent()) {
            throw new DuplicatePrimaryKeyException("Record Already exist");
        }
        return repository.save(cardPrintRequest);
    }


}
