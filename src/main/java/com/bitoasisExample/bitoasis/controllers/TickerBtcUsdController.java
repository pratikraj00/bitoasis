package com.bitoasisExample.bitoasis.controllers;

import com.bitoasisExample.bitoasis.entities.TickerBtcUsd;
import com.bitoasisExample.bitoasis.exceptions.BusinessException;
import com.bitoasisExample.bitoasis.exceptions.ControllerException;
import com.bitoasisExample.bitoasis.services.TickerBtcUsdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/TickerBtcUsd")
public class TickerBtcUsdController {

    @Autowired
    private TickerBtcUsdService tickerBtcUsdService;

    @GetMapping("/all")
    public HttpEntity<?> getAllTickerBtcUsd(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(defaultValue = "10") Integer pageSize)
    {

        try {
            List<TickerBtcUsd> tickerBtcUsdList = tickerBtcUsdService.getAllTickerBtcUsd(pageNo, pageSize);
            return ResponseEntity.ok().body(tickerBtcUsdList);
        }catch(BusinessException e)
        {
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce,HttpStatus.NOT_FOUND);
        }catch (Exception e)
        {
            ControllerException ce = new ControllerException("701", "Controller service failure !!");
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTickerBtcUsd(@RequestBody TickerBtcUsd tickerBtcUsd)
    {
        try {
            TickerBtcUsd tickerBtcUsdSave = tickerBtcUsdService.updateTickerBtcUsd(tickerBtcUsd);
            return new ResponseEntity<TickerBtcUsd>(tickerBtcUsdSave, HttpStatus.CREATED);
        } catch(BusinessException e)
        {
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce,HttpStatus.NOT_FOUND);
        }catch (Exception e)
        {
            ControllerException ce = new ControllerException("701", "Controller service failure !!");
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTickerBtcUsdById(@PathVariable(value = "id") Long id)
    {
        try {
            tickerBtcUsdService.deleteTickerBtcUsdById(id);
            return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
        }catch(BusinessException e)
        {
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllTickerBtcUsd()
    {
        try {
            tickerBtcUsdService.deleteAllTickerBtcUsd();
            return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
        }catch(BusinessException e)
        {
            ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_GATEWAY);
        }catch (Exception e)
        {
            ControllerException ce = new ControllerException("701", "Controller service failuere !!");
            return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
        }
    }
}
