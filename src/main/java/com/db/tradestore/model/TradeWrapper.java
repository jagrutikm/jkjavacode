package com.db.tradestore.model;

import java.util.ArrayList;
import java.util.List;

public class TradeWrapper {

	private List<Trade> trades ;
	
	public TradeWrapper() {
		trades = new ArrayList<>();
	}

	public List<Trade> getTrades() {
		return trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	 
	
	
}
