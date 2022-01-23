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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TickerBtcUsdServiceImpl implements TickerBtcUsdService{

    private static final Logger logger = LoggerFactory.getLogger(TickerBtcUsdServiceImpl.class);

    @Value("${url.TICKER_URL: DEFAULT no url}")
    private String tickerUrl;

    @Autowired
    private TickerBtcUsdRepository tickerBtcUsdRepository;

    public TickerBtcUsdServiceImpl(TickerBtcUsdRepository tickerBtcUsdRepository)
    {
        this.tickerBtcUsdRepository = tickerBtcUsdRepository;
    }

    @Scheduled(fixedRate =10, timeUnit = TimeUnit.SECONDS)
    public void addTickerUsdBtcJob() throws BusinessException {
        logger.info("Entered Scheduled job for ticker service class.");
        TickerBtcUsd tickerBtcUsd = new TickerBtcUsd();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
        try {
            ResponseEntity<Object[]> tickerResponse = restTemplate.exchange(tickerUrl, HttpMethod.GET, entity, Object[].class);
            // System.out.println(tickerResponse.getBody());
            if(tickerResponse == null || tickerResponse.getBody() == null)
                throw new BusinessException("600", "Ticker service did not sent output !! Check Ticker health.");

            List<Object> responses = Arrays.asList(tickerResponse.getBody());
            tickerBtcUsd = convertObjectToTicketBtcUsdEntity(responses);
            logger.debug("Created tickerBtc-Usd entity object with id :" + tickerBtcUsd.getId());
            tickerBtcUsd.setCreatedBy("tBTC_USD-API");
            tickerBtcUsd.setLastModifiedBy("tBTC_USD-API");
            tickerBtcUsdRepository.save(tickerBtcUsd);
        }catch (IllegalArgumentException e)
        {
            logger.error("Exception in scheduled job ticker service.");
            throw new BusinessException("601", "Ticker could not be saved !! " + e.getMessage());
        }catch (Exception e)
        {
            logger.error("Exception in scheduled job ticker service.");
            throw new BusinessException("602", "Service layer is not working fine " +e.getMessage());
        }
        logger.debug("Exit from ticker scheduled job service.");
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
        try {
            tickerBtcUsd.setBid(stringResponse.get(0) != null ? Float.valueOf(stringResponse.get(0)) : null);
            tickerBtcUsd.setBidSize(stringResponse.get(1) != null ? Float.valueOf(stringResponse.get(1)) : null);
            tickerBtcUsd.setAsk(stringResponse.get(2) != null ? Float.valueOf(stringResponse.get(2)) : null);
            tickerBtcUsd.setAskSize(stringResponse.get(3) != null ? Float.valueOf(stringResponse.get(3)) : null);
            tickerBtcUsd.setDailyChange(stringResponse.get(4) != null ? Float.valueOf(stringResponse.get(4)) : null);
            tickerBtcUsd.setDailyChangeRelative(stringResponse.get(5) != null ? Float.valueOf(stringResponse.get(5)) : null);
            tickerBtcUsd.setLastPrice(stringResponse.get(6) != null ? Float.valueOf(stringResponse.get(6)) : null);
            tickerBtcUsd.setVolume(stringResponse.get(7) != null ? Float.valueOf(stringResponse.get(7)) : null);
            tickerBtcUsd.setHigh(stringResponse.get(8) != null ? Float.valueOf(stringResponse.get(8)) : null);
            tickerBtcUsd.setLow(stringResponse.get(9) != null ? Float.valueOf(stringResponse.get(9)) : null);
            logger.debug("Conversion of ticker api object to ticker-btc-usd entity is successful.");
        } catch (Exception e)
        {
            logger.error("Exception in scheduled job ticker conversion.");
        }
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
    public Page<TickerBtcUsd> getAllTickerBtcUsd(Integer pageNo, Integer pageSize) throws BusinessException {
        logger.debug("Entered the get all ticker entities in page with page_size :"+ pageSize+" and pageNo :"+ pageNo);
        Pageable paging = PageRequest.of(pageNo, pageSize);
        try {
            Page<TickerBtcUsd> pagedTickerBtcUsd = tickerBtcUsdRepository.findAll(paging);
            if (pagedTickerBtcUsd != null && pagedTickerBtcUsd.hasContent()) {
                logger.debug("Return from get all ticker entities .");
            } else {
                logger.debug("No data found for ticker.");
            }
            return pagedTickerBtcUsd;
        } catch (Exception e) {
            logger.error("Exception in get all ticker service Impl");
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
        logger.debug("Entered ticker bts -usd update for ticker id : "+ tickerBtcUsd.getId());
        try {
            if(tickerBtcUsd == null)
                return TickerBtcUsd.builder().build();
            Optional<TickerBtcUsd> tickerBtcUsdOptional = tickerBtcUsdRepository.findById(tickerBtcUsd.getId());
            if(tickerBtcUsdOptional.isPresent())
            {
                tickerBtcUsd = manageAudits(tickerBtcUsd, "Local_API");
                TickerBtcUsd tickerBtcUsdSave = tickerBtcUsdRepository.save(tickerBtcUsd);
                return tickerBtcUsdSave;
            }
            else {
                return null;
            }
        }catch (Exception e)
        {
            throw new BusinessException("606", "Update operation failed for ticker Id = "
                    +tickerBtcUsd.getId()+". "+e.getMessage());
        }
    }

    @Override
    public void deleteTickerBtcUsdById(long id) throws BusinessException {
        logger.debug("Delete called for ticker btc-usd with id : "+ id);
        tickerBtcUsdRepository.deleteById(id);
        logger.error("Error in delete  of ticker with id : "+ id);
    }

    @Override
    public void deleteAllTickerBtcUsd() throws BusinessException {
        try {
            logger.debug("Complete delete of ticker btc usd entity called for ticker btc-usd");
            tickerBtcUsdRepository.deleteAll();
            logger.debug("Complete delete of ticker btc-usd database values.");
        }catch (Exception e)
        {
            logger.error("Unable to complete delete all.");
            throw new BusinessException("800","Delete operation for complete DB raised an issue.");
        }
    }
}
