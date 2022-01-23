/**
 * Service Impl class to handle the ticker btc-usd api and perform crud operations over it.
 */

package com.bitoasisExample.bitoasis.servicesImpl;

import com.bitoasisExample.bitoasis.entities.TickerBtcUsd;
import com.bitoasisExample.bitoasis.exceptions.BusinessException;
import com.bitoasisExample.bitoasis.repositories.TickerBtcUsdRepository;
import com.bitoasisExample.bitoasis.services.TickerBtcUsdService;
import com.bitoasisExample.bitoasis.servicesImpl.mapper.TicketBtcUsdConvertorResponseToEntity;
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

    /**
     * Scheduler Function to call ticker btc-usd api to fetch JSON array every 10 seconds
     * Return type void
     * @throws BusinessException
     */
    @Scheduled(fixedRate =10, timeUnit = TimeUnit.SECONDS)
    public void addTickerUsdBtcJob() throws BusinessException {
        logger.info("Entered Scheduled job for ticker service class.");

        // Preparing the Rest template and entity
        TickerBtcUsd tickerBtcUsd = new TickerBtcUsd();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
        try {
            // calling the ticker btc-usd url
            ResponseEntity<Object[]> tickerResponse = restTemplate.exchange(tickerUrl, HttpMethod.GET, entity, Object[].class);
            // System.out.println(tickerResponse.getBody());

            if(tickerResponse == null || tickerResponse.getBody() == null)
                throw new BusinessException("600", "Ticker service did not sent output !! Check Ticker health.");

            List<Object> responses = Arrays.asList(tickerResponse.getBody());

            // Converting to a valid Entity
            TicketBtcUsdConvertorResponseToEntity ticketBtcUsdConvertorResponseToEntity = new TicketBtcUsdConvertorResponseToEntity();
            tickerBtcUsd = ticketBtcUsdConvertorResponseToEntity.convertObjectToTicketBtcUsdEntity(responses);
            logger.debug("Created tickerBtc-Usd entity object with id :" + tickerBtcUsd.getId());

            // saving the final dao
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

    /**
     * Service to get all the ticker btc-usd
     */
    @Override
    public Page<TickerBtcUsd> getAllTickerBtcUsd(Integer pageNo, Integer pageSize) throws BusinessException {
        logger.debug("Entered the get all ticker entities in page with page_size :"+ pageSize+" and pageNo :"+ pageNo);
        Sort sort = Sort.by("createdDate").descending();
        Pageable paging = PageRequest.of(pageNo, pageSize, sort);
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

    /**
     * Service to get a particular ticker btc-usd
     */
    @Override
    public TickerBtcUsd getTickerBtcUsdById(long id) {
        logger.info("Get Ticker by id called for id :" + id);
        return tickerBtcUsdRepository.findById(id).get();
    }

    /**
     * Service to update a particular ticker btc-usd
     */
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

    /**
     * Service to delete a particular ticker btc-usd
     */
    @Override
    public void deleteTickerBtcUsdById(long id) throws BusinessException {
        logger.debug("Delete called for ticker btc-usd with id : "+ id);
        tickerBtcUsdRepository.deleteById(id);
        logger.error("Error in delete  of ticker with id : "+ id);
    }

    /**
     * Service to delete all the ticker btc-usd
     */
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
