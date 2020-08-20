package com.db.tradestore.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.db.tradestore.model.Trade;

public interface TradeService {

	 void save(Trade trade) throws InterruptedException, ExecutionException;		 
	 
	 List<Trade> getTrade() ;	  	 
	 Optional <Trade> getTradeById(String tradeId) ;
	
}
