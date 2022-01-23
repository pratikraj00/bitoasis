package com.bitoasisExample.bitoasis.controllers;

import com.bitoasisExample.bitoasis.entities.TickerBtcUsd;
import com.bitoasisExample.bitoasis.exceptions.BusinessException;
import com.bitoasisExample.bitoasis.exceptions.ControllerException;
import com.bitoasisExample.bitoasis.services.TickerBtcUsdService;
import com.bitoasisExample.bitoasis.servicesImpl.TickerBtcUsdServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/TickerBtcUsd")
public class TickerBtcUsdController {

    @Value("${app.name : Default Ticker_Bitoasis}")
    private String appName;

    private static final Logger logger = LoggerFactory.getLogger(TickerBtcUsdController.class);

    @Autowired
    private TickerBtcUsdService tickerBtcUsdService;

    @GetMapping("/all")
    @ApiOperation("Returns all ticker btc-usd from database.")
    public ResponseEntity<?> getAllTickerBtcUsd(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(defaultValue = "100") Integer pageSize)
    {
        logger.info("ticker btc-usd controller called to fetch all ticker btc-usd table. ");
        try {
            Page<TickerBtcUsd> tickerBtcUsdPage = tickerBtcUsdService.getAllTickerBtcUsd(pageNo, pageSize);
            logger.debug("Return successful from get all ticker btc-usd.");
            if(tickerBtcUsdPage.getContent().isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok().body(tickerBtcUsdPage);
        }catch(BusinessException e)
        {
            logger.error("Business Exception during fetching of all ticker entities.");
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce,HttpStatus.NOT_FOUND);
        }catch (Exception e)
        {
            logger.error("Exception during fetching of all ticker entities.");
            ControllerException ce = new ControllerException("701", "Controller service failure !!");
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTickerBtcUsdById(@PathVariable long id)
    {
        logger.info("ticker btc-usd controller called to fetch ticker btc-usd of id : "+ id);
        try {
            TickerBtcUsd tickerBtcUsdPage = tickerBtcUsdService.getTickerBtcUsdById(id);
            logger.debug("Return successful from get all ticker btc-usd for ticker id :"+ id);
            return ResponseEntity.ok().body(tickerBtcUsdPage);
        }catch (Exception e)
        {
            logger.error("Exception during fetching of ticker id :"+ id);
            ControllerException ce = new ControllerException("701", "Controller service failure !!");
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTickerBtcUsd(@RequestBody TickerBtcUsd tickerBtcUsd)
    {
        try {
            logger.info("Entered Ticker Controller update ticker data with id "+ tickerBtcUsd.getId());
            TickerBtcUsd tickerBtcUsdSave = tickerBtcUsdService.updateTickerBtcUsd(tickerBtcUsd);
            logger.debug("Completed updation for ticker entity with id "+ tickerBtcUsd.getId());
            return new ResponseEntity<TickerBtcUsd>(tickerBtcUsdSave, HttpStatus.OK);
        } catch(BusinessException e)
        {
            logger.error("Business Exception during update of ticker entity with id "+ tickerBtcUsd);
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce,HttpStatus.NOT_FOUND);
        }catch (Exception e)
        {
            logger.error("Exception during update of ticker entity with id "+ tickerBtcUsd);
            ControllerException ce = new ControllerException("701", "Controller service failure !!");
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTickerBtcUsdById(@PathVariable(value = "id") Long id)
    {
        logger.info("Entered API controller for delete called for ticker id :"+ id);
        try {
            tickerBtcUsdService.deleteTickerBtcUsdById(id);
            logger.debug("Successfully Completed delete for ticker id :"+ id);
            return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
        }catch(BusinessException e)
        {
            logger.error("Exception raised during delete for ticker id :"+ id);
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllTickerBtcUsd()
    {
        logger.info("Delete all values of ticker btc-usd called in api controller.");
        try {
            tickerBtcUsdService.deleteAllTickerBtcUsd();
            logger.debug("Delete all values of ticker btc-usd completed in api controller.");
            return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
        }catch(BusinessException e)
        {
            logger.error("Delete all values of ticker btc-usd EXCEPTION occurred in api controller.");
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_GATEWAY);
        }catch (Exception e)
        {
            logger.info("Delete all values of ticker btc-usd EXCEPTION occurred in api controller.");
            ControllerException ce = new ControllerException("701", "Controller service failuere !!");
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }
    }
}
