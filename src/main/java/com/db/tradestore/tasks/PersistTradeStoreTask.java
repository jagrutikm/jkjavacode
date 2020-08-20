package com.db.tradestore.tasks;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepo;

public class PersistTradeStoreTask implements Callable<Boolean >{
	private Trade trade;
	
	 
	private TradeRepo repo;
	
	public PersistTradeStoreTask(Trade trade2,TradeRepo repo) {
		this.trade = trade2;
		this.repo = repo;
	}

 
	
	
	@Override
	public Boolean  call() throws Exception {	 	 
		System.out.println(" trade in PersistTradeStoreTask "+trade);
		repo.save(trade);
		
		return true;
	}

}
