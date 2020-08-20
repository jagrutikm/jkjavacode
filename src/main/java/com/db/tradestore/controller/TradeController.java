package com.db.tradestore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.db.tradestore.TradeStoreApplication;
import com.db.tradestore.exception.InvalidTradeVersionException;
import com.db.tradestore.model.Trade;
import com.db.tradestore.model.TradeWrapper;
import com.db.tradestore.service.TradeServiceImpl;

@RestController
@RequestMapping("/trade")
public class TradeController {
	private static final Logger LOGGER = LogManager.getLogger(TradeStoreApplication.class);

	
	@Autowired
	private TradeServiceImpl tradeService;
	
	@GetMapping(value="/getTrade")
	public List <Trade> getTrade() {	
		LOGGER.info("*** Retrieving all the trades *****");		
		return  tradeService.getTrade();
		
	}
	
	@GetMapping(value="/getTradeById/{tradeId}")
	public Optional<Trade> getTrade(@PathVariable String tradeId ) {	
		LOGGER.info("*** Retrieving details of the trade*****" +tradeId );
		return  tradeService.getTradeById(tradeId);
	}
		
 
	@PostMapping(value="/savetrade")
    public ResponseEntity<List<String>>  saveTrade(@RequestBody TradeWrapper wrapper) throws InterruptedException, ExecutionException   {		 
		LOGGER.info("*** Saving trades *****" +wrapper.getTrades() );	
        List<String> response = new ArrayList<String>();        
        for (Trade trade: wrapper.getTrades()){        	
        	try {
				tradeService.save(trade);				
			} catch (InvalidTradeVersionException   ie) {				
				ie.printStackTrace();
				return new ResponseEntity<>(null, null, HttpStatus.UNPROCESSABLE_ENTITY);
			} catch (Exception   e) {				
				e.printStackTrace();
				return new ResponseEntity<>(null, null, HttpStatus.UNPROCESSABLE_ENTITY);
			} 
        	LOGGER.info("*** Processed trade *****" +trade.getTradeId() );	
        	response.add("Processed trade: "+trade.getTradeId()  );
        }
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
        
        
    }
}
