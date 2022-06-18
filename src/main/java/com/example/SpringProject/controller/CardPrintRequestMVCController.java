package com.example.SpringProject.controller;

import com.example.SpringProject.model.CardPrintRequest;
import com.example.SpringProject.repository.CardPrintRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("cp-request-mvc")
public class CardPrintRequestMVCController {
    @Autowired
    CardPrintRequestRepository cpRepository;

    @RequestMapping("/add-cp-page")
    public ModelAndView addCPRequestPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cpRequest", new CardPrintRequest());
        modelAndView.setViewName("addCPRequest");
        return modelAndView;
    }

    @RequestMapping("/addCPRequest")
    public String addCPRequest(@Valid @ModelAttribute("cpRequest") CardPrintRequest cpRequest, Model model) {
        CardPrintRequest cardPrintRequest = cpRepository.save(cpRequest);
        model.addAttribute("ipAddress",cpRequest.getIpAddress());
        model.addAttribute("branchCode",cpRequest.getBranchCode());
        model.addAttribute("cardPAN",cpRequest.getCardPAN());
        model.addAttribute("personnelCode",cpRequest.getPersonnelCode());
        model.addAttribute("issuedDate",cpRequest.getIssuedDate());
        return "showCPRequest";
    }
}
