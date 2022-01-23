package com.bitoasisExample.bitoasis.servicesImpl;


import com.bitoasisExample.bitoasis.BitoasisApplication;
import com.bitoasisExample.bitoasis.entities.AbstractAuditingEntity;
import com.bitoasisExample.bitoasis.entities.TickerBtcUsd;
import com.bitoasisExample.bitoasis.exceptions.BusinessException;
import com.bitoasisExample.bitoasis.repositories.TickerBtcUsdRepository;
import com.bitoasisExample.bitoasis.services.TickerBtcUsdService;
import com.bitoasisExample.bitoasis.servicesImpl.mapper.TicketBtcUsdDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.jayway.jsonpath.TypeRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TickerBtcUsdServiceImpl implements TickerBtcUsdService{

    private static final Logger logger = LoggerFactory.getLogger(TickerBtcUsdServiceImpl.class);

    @Autowired
    private TickerBtcUsdRepository tickerBtcUsdRepository;

    @Scheduled(fixedRate =10, timeUnit = TimeUnit.SECONDS)
    public void addTickerUsdBtcJob() throws BusinessException {
        logger.info("Entered Scheduled job for ticker service class.");
        TickerBtcUsd tickerBtcUsd = new TickerBtcUsd();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
        try {
            ResponseEntity<Object[]> tickerResponse = restTemplate.exchange("https://api-pub.bitfinex.com/v2/ticker/tBTCUSD", HttpMethod.GET, entity, Object[].class);
            //String[] tickerResponseString = tickerResponse.getBody().split(",");
            System.out.println(tickerResponse.getBody());
            System.out.println(Arrays.asList(tickerResponse.getBody()).get(0));
            if(tickerResponse == null || tickerResponse.getBody() == null)
                throw new BusinessException("600", "Ticker service did not sent output !! Check Ticker health.");
            List<Object> responses = Arrays.asList(tickerResponse.getBody());
            tickerBtcUsd = convertObjectToTicketBtcUsdEntity(responses);
            tickerBtcUsd.setCreatedBy("tBTC_USD-API");
            tickerBtcUsd.setLastModifiedBy("tBTC_USD-API");
            tickerBtcUsdRepository.save(tickerBtcUsd);
        }catch (IllegalArgumentException e)
        {
            throw new BusinessException("601", "Ticker could not be saved !! " + e.getMessage());
        }catch (Exception e)
        {
            throw new BusinessException("602", "Service layer is not working fine " +e.getMessage());
        }
    }

    private TickerBtcUsd convertObjectToTicketBtcUsdEntity(List<Object> response)
    {
        TickerBtcUsd tickerBtcUsd = new TickerBtcUsd();
        List<String> stringResponse = new ArrayList<>();
        for(Object obj : response)
        {
            stringResponse.add(obj.toString());
            System.out.print(obj+ " ,");
        }
        tickerBtcUsd.setBid(stringResponse.get(0) != null ? Float.valueOf(stringResponse.get(0)) : null);
        tickerBtcUsd.setBid(stringResponse.get(1) != null ? Float.valueOf(stringResponse.get(1)) : null);
        tickerBtcUsd.setAsk(stringResponse.get(2) != null ? Float.valueOf(stringResponse.get(2)) : null);
        tickerBtcUsd.setAskSize(stringResponse.get(3) != null ? Float.valueOf(stringResponse.get(3)) : null);
        tickerBtcUsd.setDailyChange(stringResponse.get(4) != null ? Float.valueOf(stringResponse.get(4)) : null);
        tickerBtcUsd.setDailyChangeRelative(stringResponse.get(5) != null ? Float.valueOf(stringResponse.get(5)) : null);
        tickerBtcUsd.setLastPrice(stringResponse.get(6) != null ? Float.valueOf(stringResponse.get(6)) : null);
        tickerBtcUsd.setVolume(stringResponse.get(7) != null ? Float.valueOf(stringResponse.get(7)) : null);
        tickerBtcUsd.setHigh(stringResponse.get(8) != null ? Float.valueOf(stringResponse.get(8)) : null);
        tickerBtcUsd.setLow(stringResponse.get(9) != null ? Float.valueOf(stringResponse.get(9)) : null);

        return tickerBtcUsd;
    }

    private TickerBtcUsd manageAudits(TickerBtcUsd tickerBtcUsd, String  lastModifiedBy)
    {
        TickerBtcUsd tickerBtcUsdOld = tickerBtcUsdRepository.findById(tickerBtcUsd.getId()).get();
        if(tickerBtcUsdOld != null) {
            tickerBtcUsd.setCreatedDate(tickerBtcUsdOld.getCreatedDate());
            tickerBtcUsd.setCreatedBy(tickerBtcUsdOld.getCreatedBy());
        }
        tickerBtcUsd.setLastModifiedBy(lastModifiedBy);

        return tickerBtcUsd;
    }

    @Override
    public List<TickerBtcUsd> getAllTickerBtcUsd(Integer pageNo, Integer pageSize) throws BusinessException {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<TickerBtcUsd> pagedTickerBtcUsd = tickerBtcUsdRepository.findAll(paging);
        if (pagedTickerBtcUsd.hasContent()) {
            return pagedTickerBtcUsd.getContent();
            } else {
            throw new BusinessException("604", "Ticker table is empty ");
            }
    }

    @Override
    public TickerBtcUsd getTickerBtcUsdById(long id) {
        logger.info("Get Ticker by id called for id :" + id);
        return tickerBtcUsdRepository.findById(id).get();
    }

    @Override
    public TickerBtcUsd updateTickerBtcUsd(TickerBtcUsd tickerBtcUsd) throws BusinessException {
        try {
            tickerBtcUsd = manageAudits(tickerBtcUsd, "Local_API");
            TickerBtcUsd tickerBtcUsdSave = tickerBtcUsdRepository.save(tickerBtcUsd);
            return tickerBtcUsdSave;
        }catch (Exception e)
        {
            throw new BusinessException("606", "Update operation failed for ticker Id = "
                    +tickerBtcUsd.getId()+". "+e.getMessage());
        }
    }

    @Override
    public void deleteTickerBtcUsdById(long id) throws BusinessException {
        tickerBtcUsdRepository.deleteById(id);
    }

    @Override
    public void deleteAllTickerBtcUsd() throws BusinessException {
        tickerBtcUsdRepository.deleteAll();
    }
}
