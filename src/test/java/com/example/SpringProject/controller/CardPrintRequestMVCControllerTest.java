package com.example.SpringProject.controller;

import com.example.SpringProject.SpringProjectApplication;
import com.example.SpringProject.controller.CardPrinterRequestController;
import com.example.SpringProject.model.CardPrintRequest;
import com.example.SpringProject.model.CardPrintRequestPK;
import com.example.SpringProject.repository.CardPrintRequestRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@EnableWebMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpringProjectApplication.class)
class CardPrintRequestMVCControllerTest {

    @Mock
    @Autowired
    private CardPrintRequestRepository mockRepository;

    @InjectMocks
    @Autowired
    private CardPrintRequestMVCController CPRMVCController;

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
    public void WHEN_add_card_print_request_url_requested_THEN_show_page() throws Exception {
        this.mockMvc.perform(get("/cp-request-mvc/add-cp-page")).andDo(print())
                .andExpect(view().name("addCPRequest"));
    }

}