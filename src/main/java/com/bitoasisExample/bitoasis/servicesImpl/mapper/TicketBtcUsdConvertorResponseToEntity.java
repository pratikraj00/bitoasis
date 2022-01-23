package com.bitoasisExample.bitoasis.servicesImpl.mapper;

import com.bitoasisExample.bitoasis.entities.TickerBtcUsd;
import com.bitoasisExample.bitoasis.servicesImpl.TickerBtcUsdServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TicketBtcUsdConvertorResponseToEntity {

    private static final Logger logger = LoggerFactory.getLogger(TickerBtcUsdServiceImpl.class);

    public TickerBtcUsd convertObjectToTicketBtcUsdEntity(List<Object> response)
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
}
