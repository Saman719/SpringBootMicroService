package com.example.SpringProject.init;

import com.example.SpringProject.SpringProjectApplication;
import com.example.SpringProject.model.CardPrintRequest;
import com.example.SpringProject.model.CardPrintRequestPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InitializerRunner implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        List<CardPrintRequest> requests = new ArrayList<>();
        requests.add(new CardPrintRequest(new CardPrintRequestPK("192.168.1.120", "12345"), "9990001", new Date(), "5022291012341234"));
        requests.add(new CardPrintRequest(new CardPrintRequestPK("10.20.12.35", "11000"), "9990001", new Date(), "5022291012342589"));
        requests.add(new CardPrintRequest(new CardPrintRequestPK("10.20.15.60", "11253"), "9990001", new Date(), "5022291012344528"));
        requests.add(new CardPrintRequest(new CardPrintRequestPK("10.20.12.65", "12452"), "9990001", new Date(), "5022291012342766"));
        requests.add(new CardPrintRequest(new CardPrintRequestPK("10.20.12.45", "52365"), "9990001", new Date(), "5022291012458266"));
        requests.add(new CardPrintRequest(new CardPrintRequestPK("10.20.12.45", "12020"), "9990001", new Date(), "5022291012455220"));
        requests.add(new CardPrintRequest(new CardPrintRequestPK("10.20.12.45", "78945"), "9990001", new Date(), "6219291045697785"));
        requests.add(new CardPrintRequest(new CardPrintRequestPK("10.20.12.45", "66554"), "9990001", new Date(), "6037291012458266"));

//        saveEntity(requests);
        System.out.println("From initializer :)");
    }

    private void saveEntity(List<CardPrintRequest> requests) {
        String sql = "INSERT INTO t_CardPrintRequest (c_ipaddress, c_branchcode, c_personnelcode, c_issueddate, c_cardpan) VALUES (?, ?, ?, ?, ?)";
        requests.forEach(cardPrintRequest -> jdbcTemplate.update(sql,
                cardPrintRequest.getCardPrintRequestPK().getIpAddress(), cardPrintRequest.getCardPrintRequestPK().getBranchCode(),
                cardPrintRequest.getPersonnelCode(), cardPrintRequest.getIssuedDate(), cardPrintRequest.getCardPAN()));
    }
}
