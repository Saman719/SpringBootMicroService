package com.example.SpringProject.controller;

import com.example.SpringProject.aspect.ExecuteTime;
import com.example.SpringProject.aspect.LogMethod;
import com.example.SpringProject.exception.DuplicatePrimaryKeyException;
import com.example.SpringProject.exception.RestControllerException;
import com.example.SpringProject.model.CardPrintRequest;
import com.example.SpringProject.model.CardPrintRequestPK;
import com.example.SpringProject.repository.CardPrintRequestRepository;
import com.example.SpringProject.service.CardPrintRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cp-request")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CardPrinterRequestController {
    @Autowired
    CardPrintRequestRepository cpRepository;
    @Autowired
    CardPrintRequestService cpService;

    @GetMapping("/all")
    @ExecuteTime
    public ResponseEntity<List<CardPrintRequest>> getAllCPRequest(@RequestHeader Map<String, String> headers) {
        headers.forEach((s, s2) -> System.out.println("Header = " + s + " Value = " + s2));
        return ResponseEntity.ok(cpRepository.findAll());
    }

    @Valid
    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE)
    @LogMethod
    public ResponseEntity<CardPrintRequest> insertCPRequest(@Valid @RequestBody CardPrintRequest cardPrintRequest) throws Exception {
        try {
            CardPrintRequest request = cpService.insertNewCardPrintRequest(cardPrintRequest);
            return ResponseEntity.ok(request);
        } catch (RestControllerException e) {
            if (e instanceof DuplicatePrimaryKeyException) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "IpAddress and BranchCode combination already exist");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update/{branchcode}/{cardpan}")
    @Valid
    public String updatePANNumberByBranchCode(@PathVariable(value = "branchcode", required = true) String branchCode, @PathVariable(value = "cardpan", required = true) String cardPAN) {
        cpRepository.updatePanNumberByBranchCode(cardPAN, branchCode);
        return cardPAN;
    }

    @PatchMapping(value = "/update/personnelcode/{personnelCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Valid
    public ResponseEntity<CardPrintRequest> patchCard(@Valid @RequestBody CardPrintRequestPK cardPrintRequestPK,
                                                      @PathVariable(value = "personnelCode", required = true) String personnelCode) {
        Optional<CardPrintRequest> requestByPKOptional = cpRepository.findById(cardPrintRequestPK);
        if (requestByPKOptional.isPresent()) {
            requestByPKOptional.get().setPersonnelCode(personnelCode);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        cpRepository.save(requestByPKOptional.get());
        return ResponseEntity.ok(requestByPKOptional.get());
    }

    @DeleteMapping(value = "/delete/ip-branch")
    @ResponseBody
    @Valid
    public ResponseEntity<CardPrintRequest> deleteCard(@Valid @RequestBody CardPrintRequestPK cardPrintRequestPK) {
        Optional<CardPrintRequest> requestByPKOptional = cpRepository.findById(cardPrintRequestPK);
        if (requestByPKOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        cpRepository.delete(requestByPKOptional.get());
        return ResponseEntity.ok(requestByPKOptional.get());
    }

    @DeleteMapping(value = "/delete/cardpan/{cardpan}")
    @ResponseBody
    @Valid
    public ResponseEntity<String> deleteCardByCardPan(@PathVariable(value = "cardpan", required = true) String cardPAN) {
        List<CardPrintRequest> cardPrintRequests = cpRepository.deleteAllByCardPAN(cardPAN);
        return ResponseEntity.ok("Total Number Of : " + cardPrintRequests.size() + " Has Been Deleted Successfully");
    }

}
