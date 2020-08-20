package com.db.tradestore;


import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.db.tradestore.exception.InvalidTradeVersionException;
import com.db.tradestore.model.Trade;
import com.db.tradestore.model.TradeWrapper;
import com.db.tradestore.service.TradeServiceImpl;

import java.time.temporal.ChronoUnit;

 
@RunWith(SpringRunner.class)		
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)	

public class TradeStoreApplicationTests {
	@Autowired 
	private TestRestTemplate 	restTemplate;
	
	@MockBean
	TradeServiceImpl tradeService;
	
	private LocalDate today;
	private LocalDate nextYear;
	private LocalDate prevyear;
	private Date currentdate;
	private Date nextYearDate;
	private Date prevYearDate;
	
	
	@LocalServerPort
    int randomServerPort;
	
	
	@Before
	public void setUp() {
		today = LocalDate.now();
		nextYear = today.plus(1, ChronoUnit.YEARS);
		prevyear = today.minus(1, ChronoUnit.YEARS);
		currentdate = java.sql.Date.valueOf(today);
		nextYearDate = java.sql.Date.valueOf(nextYear);
		prevYearDate = java.sql.Date.valueOf(prevyear);
		
	}
	
	@After
	public void tearDown() {
		today=null;
		nextYear=null;
		currentdate=null;
		nextYearDate=null;
		
	}
	 
	 
	@Test
	public void testGetTrade() {		 
		ResponseEntity <List> response = restTemplate.getForEntity("/trade/getTrade",List.class);
		System.out.println(" response "+response);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); 
	}
	
	@Test
	public void testGetByTradeId() throws URISyntaxException {	
		testCreateTrade();
		ResponseEntity    response = restTemplate.getForEntity("/trade/getTradeById/T1",Trade.class);	
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testCreateTrade() throws URISyntaxException {		 
	     URI uri = new URI("/trade/savetrade");
	     TradeWrapper tw = new TradeWrapper();
	     Trade t = new Trade("T4", 4, null, nextYearDate);
	     List<Trade> tradesList = new ArrayList<>();
	     tw.setTrades(tradesList);
	     
	     HttpHeaders headers = new HttpHeaders();
	     headers.set("X-COM-PERSIST", "true");      	 
	     HttpEntity<TradeWrapper> request = new HttpEntity<>(tw,headers);
        System.out.println(" request "+request);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);       
        //Verify request succeed
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        uri = null;
        t=null;
        headers = null;
	}
	
	
	
 
 
}


