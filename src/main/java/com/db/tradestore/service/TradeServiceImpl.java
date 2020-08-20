package com.db.tradestore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.db.tradestore.TradeStoreApplication;
import com.db.tradestore.exception.InvalidTradeVersionException;
import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepo;
import com.db.tradestore.tasks.*;

@Service
public class TradeServiceImpl implements TradeService {
	@Autowired
	TradeRepo repo;
	private static final Logger LOGGER = LogManager.getLogger(TradeStoreApplication.class);
	
	ExecutorService service = Executors.newFixedThreadPool(20);
	
	public void save(Trade trade) throws InvalidTradeVersionException,InterruptedException, ExecutionException  {
		LOGGER.info("*** Trade Service layer save*****" +trade.getTradeId());				 
		Optional<Trade> t  =  repo.findById(trade.getTradeId()) ;		
		int prevVersionNumber = (t.isPresent()) ? t.get().getVersion() :0;
		Boolean isValid;
		Boolean isSaved;
		Boolean isValidationProcessDone =false;
		
		LOGGER.info("*** Trade Service layer prevVersionNumber *****"+prevVersionNumber + " of trade " +trade.getTradeId());		 		 
		
		//Every trade order has to go through the task of Validation before createOrUpdate. 
		Future<Boolean> futureIsValid= 	service.submit(new ValidationUtilsTask(trade,prevVersionNumber));		
		
		LOGGER.info(" futureIsValid.isDone())   ============== "+ futureIsValid.isDone());
				
		while(!futureIsValid.isDone()) {
			LOGGER.info("validating ...");
		    Thread.sleep(300);
		}
		
		isValid = (futureIsValid.isDone()) ? futureIsValid.get() :false;		
		LOGGER.info(" isValid  ============== "+isValid);
		
		isValidationProcessDone = (futureIsValid.isDone()) ?  true:false;	
		 ; 
		LOGGER.info(" futureIsValid.isDone())   ============== "+ futureIsValid.isDone());
		if(futureIsValid.isDone() && isValid) {
			Future<Boolean> futureIsSaved = service.submit(new PersistTradeStoreTask(trade,repo)) ;		
			isSaved = (futureIsSaved.isDone()) ? futureIsSaved.get() :false;  // However, this is blocking operation. should make use of CompletableFuture instead
			while(!futureIsSaved.isDone()) {
				LOGGER.info("saving ...");
			    Thread.sleep(300);
			   
			}		 		 
			
			
		} 
		LOGGER.info(" isValidationProcessDone "+isValidationProcessDone + "isValid " + isValid);
		if(isValidationProcessDone && !isValid)			  
			throw new InvalidTradeVersionException(trade.getTradeId());	
							
			
	}	
	
	public List<Trade> getTrade(){
		LOGGER.info(" fetched "+repo.findAll());
		List<Trade> tradelist = new ArrayList<Trade>();  
		repo.findAll().forEach(trade -> tradelist.add(trade));  
		return tradelist;  
//		return   repo.findAll();
	}
	
	public Optional <Trade> getTradeById(String tradeId){
		return    repo.findById(tradeId);
	}
	
}
