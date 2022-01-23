package com.bitoasisExample.bitoasis.servicesImpl;

import com.bitoasisExample.bitoasis.entities.TickerBtcUsd;
import com.bitoasisExample.bitoasis.exceptions.BusinessException;
import com.bitoasisExample.bitoasis.repositories.TickerBtcUsdRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TickerBtcUsdServiceImplTest {

    private final Integer PAGE_NO= 0;
    private final Integer PAGE_SIZE =1;
    @Mock
    private TickerBtcUsdRepository tickerBtcUsdRepository;

    private TickerBtcUsdServiceImpl tickerBtcUsdServiceImpl;

    private Pageable paging;

    @BeforeEach
    public void setUp()
    {
        this.tickerBtcUsdServiceImpl = new TickerBtcUsdServiceImpl(tickerBtcUsdRepository);
        paging = PageRequest.of(PAGE_NO, PAGE_SIZE);
    }

    @Test
    void addTickerUsdBtcJob() {

    }

    @Test
    void getAllTickerBtcUsd() throws BusinessException {
        tickerBtcUsdServiceImpl.getAllTickerBtcUsd(PAGE_NO, PAGE_SIZE);
        verify(tickerBtcUsdRepository).findAll(paging);
    }

    @Test
    void getTickerBtcUsdById() {

    }

    @Test
    void updateTickerBtcUsd() {

        // TickerBtcUsd tickerBtcUsd = tickerBtcUsdServiceImpl.updateTickerBtcUsd();
    }

    @Test
    void deleteTickerBtcUsdById() {
    }

    @Test
    void deleteAllTickerBtcUsd() {
    }

    @AfterEach
    void deleteLocaltearDown()
    {
        // auto closeable tear down
    }
}