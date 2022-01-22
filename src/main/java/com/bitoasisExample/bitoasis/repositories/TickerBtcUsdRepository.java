package com.bitoasisExample.bitoasis.repositories;

import com.bitoasisExample.bitoasis.entities.TickerBtcUsd;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerBtcUsdRepository extends PagingAndSortingRepository<TickerBtcUsd, Long> {

}
