package com.example.SpringProject.controller;

import com.example.SpringProject.SpringProjectApplication;
import com.example.SpringProject.controller.CardPrinterRequestController;
import com.example.SpringProject.model.CardPrintRequest;
import com.example.SpringProject.model.CardPrintRequestPK;
import com.example.SpringProject.repository.CardPrintRequestRepository;
import com.example.SpringProject.service.CardPrintRequestService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@EnableWebMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpringProjectApplication.class)
public class CardPrinterRequestControllerTest {

    @MockBean
    @Autowired
    private CardPrintRequestRepository mockRepository;

    @MockBean
    @Autowired
    private CardPrintRequestService cpService;

    @InjectMocks
    @Autowired
    private CardPrinterRequestController CPRController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void WHEN_request_for_all_request_return_all() {
        when(mockRepository.findAll()).thenReturn(Collections.singletonList(new CardPrintRequest
                (new CardPrintRequestPK("10.20.12.35", "54256"), "9990011", new Date(), "50299291025658753")));
        assertTrue(CPRController.getAllCPRequest(new HashMap<>()).getBody().size() > 0);
    }

    @Test
    public void WHEN_insert_is_successful_THEN_return_ok_status() throws Exception {
        when(cpService.insertNewCardPrintRequest(any())).thenReturn(new CardPrintRequest
                (new CardPrintRequestPK("10.20.12.35", "54256"), "9990011", new Date(), "50299291025658753"));
        this.mockMvc.perform(post("/cp-request/insert").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\n" +
                        "        \"cardPrintRequestPK\": {\n" +
                        "            \"ipAddress\": \"10.20.152.145\",\n" +
                        "            \"branchCode\": \"65845\"\n" +
                        "        },\n" +
                        "        \"personnelCode\": \"99900011\",\n" +
                        "        \"issuedDate\": \"2022-02-24\",\n" +
                        "        \"cardPAN\": \"5022291012346523\"\n" +
                        "}"
        )).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void WHEN_update_with_pan_number_THEN_return_status() throws Exception {
        doNothing().when(mockRepository).updatePanNumberByBranchCode(any(), any());
        this.mockMvc.perform(put("/cp-request/update/{branchcode}/{cardpan}", "53486", "5022291080506254")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void WHEN_update_primary_key_found_THEN_change_personnel_code() throws Exception {
        when(mockRepository.save(any())).thenReturn(new CardPrintRequest());
        when(mockRepository.findById(any())).thenReturn(Optional.of(new CardPrintRequest
                (new CardPrintRequestPK("10.20.12.35", "54256"), "9990011", new Date(), "50299291025658753")));
        this.mockMvc.perform(patch("/cp-request/update/personnelcode/{personnelCode}", "99900170").contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                "            \"ipAddress\": \"10.20.12.35\",\n" +
                "            \"branchCode\": \"19568\"\n" +
                "}")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void WHEN_update_primary_key_NOT_found_THEN_change_personnel_code() throws Exception {
        when(mockRepository.save(any())).thenReturn(new CardPrintRequest());
        when(mockRepository.findById(any())).thenReturn(Optional.empty());
        this.mockMvc.perform(patch("/cp-request/update/personnelcode/{personnelCode}", "99900170").contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                        "            \"ipAddress\": \"10.20.12.35\",\n" +
                        "            \"branchCode\": \"19568\"\n" +
                        "}")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));
    }

    @Test
    public void WHEN_delete_by_card_pan_AND_nothing_is_deleted_THEN_get_zero_number_of_deleted_card_print_requests() throws Exception {
        when(mockRepository.deleteAllByCardPAN(any())).thenReturn(Collections.emptyList());
        MvcResult result = this.mockMvc.perform(delete("/cp-request/delete/cardpan/{cardpan}", "5022291080506254")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("0"));
    }

    @Test
    public void WHEN_delete_by_card_pan_THEN_get_number_of_deleted_card_print_requests() throws Exception {
        List<CardPrintRequest> cardPrintRequests = new ArrayList<>();
        cardPrintRequests.add(new CardPrintRequest());
        cardPrintRequests.add(new CardPrintRequest());
        when(mockRepository.deleteAllByCardPAN(any())).thenReturn(cardPrintRequests);
        MvcResult result = this.mockMvc.perform(delete("/cp-request/delete/cardpan/{cardpan}", "5022291080506254")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(String.valueOf(cardPrintRequests.size())));
    }
}
