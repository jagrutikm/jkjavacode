package com.db.tradestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class TradeStoreApplication {

	private static final Logger LOGGER = LogManager.getLogger(TradeStoreApplication.class);
	
	public static void main(String[] args) {
		LOGGER.info("*** Starts Trade Store App *****");
		SpringApplication.run(TradeStoreApplication.class, args);
		
        LOGGER.debug("Debug level log message");
        LOGGER.error("Error level log message");
	}

}
