package com.bitoasisExample.bitoasis.repositories;

import com.bitoasisExample.bitoasis.entities.TickerBtcUsd;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TickerBtcUsdRepositoryTest {

    private final Long ID= 1L;
    private final Float HIGH =100f;

    @Autowired
    private TickerBtcUsdRepository tickerBtcUsdRepository;

    @Test
    @Order(1)
    public void TickerRepofindAllTest() {
        Pageable paging = PageRequest.of(0, 1);
        Page<TickerBtcUsd> list = tickerBtcUsdRepository.findAll(paging);
        System.out.println("print this "+ list.getContent().size());
        assertThat(list.getContent().size()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void deleteTest()
    {
        tickerBtcUsdRepository.deleteAll();
        assertThat(tickerBtcUsdRepository.count()).isEqualTo(0);
    }

    @AfterEach
    void clearRepoTearDown()
    {
        tickerBtcUsdRepository.deleteAll();
    }

    @BeforeEach
    void setUp()
    {
        // some creation of application context etc
        TickerBtcUsd tickerBtcUsd = TickerBtcUsd.builder().ask(1f)
                .bid(3f).bidSize(4f)
                .askSize(2f).dailyChange(5f).dailyChangeRelative(6f)
                .volume(7f).high(8f).low(9f)
                .build();
        tickerBtcUsd.setCreatedBy("TestRepos");
        tickerBtcUsd.setLastModifiedBy("TestRepos");
        tickerBtcUsdRepository.save(tickerBtcUsd);
    }
}
