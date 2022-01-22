package com.bitoasisExample.bitoasis.services;

import com.bitoasisExample.bitoasis.entities.TickerBtcUsd;
import com.bitoasisExample.bitoasis.exceptions.BusinessException;

import java.util.List;

public interface TickerBtcUsdService {

    List<TickerBtcUsd> getAllTickerBtcUsd(Integer pageNo, Integer pageSize) throws BusinessException;
    TickerBtcUsd getTickerBtcUsdById(long id);
    TickerBtcUsd updateTickerBtcUsd(TickerBtcUsd tickerBtcUsd) throws BusinessException;
    void deleteTickerBtcUsdById(long Id) throws BusinessException;
    void deleteAllTickerBtcUsd() throws BusinessException;
    void addTickerUsdBtcJob() throws BusinessException;
}
