/**
 * Controller project to handle the ticker btc-usd api and perform crud operations over it.
 * Owner -Pratik raj
 */

package com.bitoasisExample.bitoasis.controllers;

import com.bitoasisExample.bitoasis.entities.TickerBtcUsd;
import com.bitoasisExample.bitoasis.exceptions.BusinessException;
import com.bitoasisExample.bitoasis.exceptions.ControllerException;
import com.bitoasisExample.bitoasis.services.TickerBtcUsdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/TickerBtcUsd")
@Api(value="Resource for TickerBtcUsd Endpoints")
public class TickerBtcUsdController {

    @Value("${app.name : Default Ticker_Bitoasis}")
    private String appName;

    private static final Logger logger = LoggerFactory.getLogger(TickerBtcUsdController.class);

    @Autowired
    private TickerBtcUsdService tickerBtcUsdService;

    /**
     * Resource to get all the ticker btc-usd
     */
    @GetMapping("/all")
    @ApiOperation("Returns all ticker btc-usd from database.")
    @ApiResponses(value = {
            @ApiResponse(code= 200, message ="Successful fetch"),
            @ApiResponse(code= 204, message ="No value Retrieved"),
            @ApiResponse(code= 400, message ="Unknown exception caught")
    })
    public ResponseEntity<?> getAllTickerBtcUsd(@RequestParam(required = false,defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(required = false,defaultValue = "100") Integer pageSize)
    {
        logger.info("ticker btc-usd controller called to fetch all ticker btc-usd table. ");
        try {
            Page<TickerBtcUsd> tickerBtcUsdPage = tickerBtcUsdService.getAllTickerBtcUsd(pageNo, pageSize);
            logger.debug("Return successful from get all ticker btc-usd.");
            if(tickerBtcUsdPage.getContent().isEmpty())
                return ResponseEntity.noContent().build();
            return new ResponseEntity<Page<TickerBtcUsd>>(tickerBtcUsdPage,HttpStatus.OK);
        }catch(BusinessException e)
        {
            logger.error("Business Exception during fetching of all ticker entities.");
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            logger.error("Exception during fetching of all ticker entities.");
            ControllerException ce = new ControllerException("701", "Controller service failure !!");
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Resource to get all the ticker btc-usd by an ID
     */
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

    /**
     * Resource to update the ticker btc-usd with an id
     */
    @PutMapping("/update")
    @ApiOperation("Updates a ticker btc-usd entity")
    @ApiResponses(value = {
            @ApiResponse(code= 200, message ="Successful update"),
            @ApiResponse(code= 404, message ="No value Retrieved to update"),
            @ApiResponse(code= 400, message ="Unknown exception caught")
    })
    public ResponseEntity<?> updateTickerBtcUsd(@RequestBody TickerBtcUsd tickerBtcUsd)
    {
        try {
            logger.info("Entered Ticker Controller update ticker data with id "+ tickerBtcUsd);
            TickerBtcUsd tickerBtcUsdSave = tickerBtcUsdService.updateTickerBtcUsd(tickerBtcUsd);
            logger.debug("Completed updation for ticker entity with id "+ tickerBtcUsd.getId());
            if(tickerBtcUsdSave == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<TickerBtcUsd>(tickerBtcUsdSave, HttpStatus.OK);
        } catch(BusinessException e)
        {
            logger.error("Business Exception during update of ticker entity with id "+ tickerBtcUsd);
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            logger.error("Exception during update of ticker entity with id "+ tickerBtcUsd);
            ControllerException ce = new ControllerException("701", "Controller service failure !!");
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Resource to delete a particular ticker btc-usd
     */
    @ApiOperation("Deletes a ticker btc-usd entity")
    @ApiResponses(value = {
            @ApiResponse(code= 202, message ="Successful Delete"),
            @ApiResponse(code= 404, message ="No value Retrieved to delete")
    })
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

    /**
     * Resource to get delete all the ticker btc-usd
     */
    @ApiOperation("Deletes a ticker btc-usd entity")
    @ApiResponses(value = {
            @ApiResponse(code= 202, message ="Successful Delete"),
            @ApiResponse(code= 400, message ="Service error")
    })
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
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }catch (Exception e)
        {
            logger.info("Delete all values of ticker btc-usd EXCEPTION occurred in api controller.");
            ControllerException ce = new ControllerException("701", "Controller service failuere !!");
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }
    }
}
