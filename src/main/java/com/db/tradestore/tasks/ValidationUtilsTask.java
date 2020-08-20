package com.db.tradestore.tasks;

import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.db.tradestore.TradeStoreApplication;
import com.db.tradestore.exception.InvalidTradeVersionException;
import com.db.tradestore.model.Trade;

public class ValidationUtilsTask implements Callable<Boolean >{

	private Trade trade;
	private int prevVersionNumber;
	private static final Logger LOGGER = LogManager.getLogger(TradeStoreApplication.class);
	
	public ValidationUtilsTask(Trade trade2,int prevVersionNumber) {
		this.trade = trade2;
		this.prevVersionNumber = prevVersionNumber;
	}


	 
	
	@Override
	public Boolean  call() throws InvalidTradeVersionException,Exception {		
		Boolean isValidTrade = true;
		
		if(trade.getVersion() < prevVersionNumber) {
			LOGGER.error(" lower version is passed for  trade   "+trade ); 		
			throw new InvalidTradeVersionException(trade.getTradeId());			
			
		}
		
		int datediff =  trade.getCreatedOn().compareTo(trade.getMaturityDate()) ;					 
		 if(datediff ==1 ) {
			LOGGER.error(" MaturityDate is crossed  "+trade ); 
		    trade.setIsExpired('Y');
		} else 
			trade.setIsExpired('N');			    	
		
		return isValidTrade;
	}

}
