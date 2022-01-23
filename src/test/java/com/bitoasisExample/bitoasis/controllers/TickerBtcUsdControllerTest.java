package com.bitoasisExample.bitoasis.controllers;

import com.bitoasisExample.bitoasis.entities.TickerBtcUsd;
import com.bitoasisExample.bitoasis.exceptions.BusinessException;
import com.bitoasisExample.bitoasis.services.TickerBtcUsdService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TickerBtcUsdControllerTest {

    @InjectMocks
    TickerBtcUsdController tickerBtcUsdController;

    @Mock
    TickerBtcUsdService tickerBtcUsdService;

    Page<TickerBtcUsd> tickerBtcUsdPage;

    @BeforeEach
    void setUp() throws BusinessException {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllTickerBtcUsd_EmptyResponse() throws BusinessException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        tickerBtcUsdPage = Page.empty();
        when(tickerBtcUsdService.getAllTickerBtcUsd(0,1)).thenReturn(tickerBtcUsdPage);
        ResponseEntity<?> responseEntity = tickerBtcUsdController.getAllTickerBtcUsd(0,1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllTickerBtcUsd_E2E() throws BusinessException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        TickerBtcUsd tickerBtcUsd = TickerBtcUsd.builder().ask(1f)
                .bid(3f).bidSize(4f)
                .askSize(2f).dailyChange(5f).dailyChangeRelative(6f)
                .volume(7f).high(8f).low(9f)
                .build();
        tickerBtcUsd.setCreatedBy("TestRepos");
        tickerBtcUsd.setLastModifiedBy("TestRepos");
        List<TickerBtcUsd> tickerBtcUsdList = new ArrayList<>();
        tickerBtcUsdList.add(tickerBtcUsd);
        tickerBtcUsdPage = new PageImpl<>(tickerBtcUsdList);
        when(tickerBtcUsdService.getAllTickerBtcUsd(0,1)).thenReturn(tickerBtcUsdPage);
        ResponseEntity<?> responseEntity = tickerBtcUsdController.getAllTickerBtcUsd(0,1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    void getTickerBtcUsdById() {
        // to be implemented later as not a part of question
    }

    @Test
    void updateTickerBtcUsd() throws BusinessException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        TickerBtcUsd tickerBtcUsd = TickerBtcUsd.builder().ask(1f)
                .bid(3f).bidSize(4f)
                .askSize(2f).dailyChange(5f).dailyChangeRelative(6f)
                .volume(7f).high(8f).low(9f)
                .build();
        tickerBtcUsd.setCreatedBy("TestRepos");
        tickerBtcUsd.setLastModifiedBy("TestRepos");

        when(tickerBtcUsdService.updateTickerBtcUsd(tickerBtcUsd)).thenReturn(tickerBtcUsd);
        ResponseEntity<?> responseEntity = tickerBtcUsdController.updateTickerBtcUsd(tickerBtcUsd);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateTickerBtcUsd_Exception() throws BusinessException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<?> responseEntity = tickerBtcUsdController.updateTickerBtcUsd(null);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteTickerBtcUsdById() throws BusinessException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        TickerBtcUsd tickerBtcUsd = TickerBtcUsd.builder().ask(1f)
                .bid(3f).bidSize(4f)
                .askSize(2f).dailyChange(5f).dailyChangeRelative(6f)
                .volume(7f).high(8f).low(9f)
                .build();
        tickerBtcUsd.setCreatedBy("TestRepos");
        tickerBtcUsd.setLastModifiedBy("TestRepos");
        List<TickerBtcUsd> tickerBtcUsdList = new ArrayList<>();
        tickerBtcUsdList.add(tickerBtcUsd);
        tickerBtcUsdPage = new PageImpl<>(tickerBtcUsdList);
        ResponseEntity<?> responseEntity = tickerBtcUsdController.deleteTickerBtcUsdById(0l);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void deleteAllTickerBtcUsd() throws BusinessException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<?> responseEntity = tickerBtcUsdController.deleteAllTickerBtcUsd();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}