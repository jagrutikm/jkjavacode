package com.db.tradestore.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidTradeVersionException extends RuntimeException {
	
	public InvalidTradeVersionException(String id) {
		 super("Invalid version Id passed for Trade: "+id);
	}

}