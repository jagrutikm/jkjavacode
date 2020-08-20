package com.db.tradestore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.db.tradestore.exception.InvalidTradeVersionException;
import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepo;

@RunWith(SpringRunner.class)
@SpringBootTest

public class TradeServiceImplTest {

	@Autowired
	private TradeServiceImpl  tradeServiceImpl;
	
	@MockBean
	private TradeRepo repo;
	
	private LocalDate today;
	private LocalDate nextYear;
	private LocalDate prevyear;
	private Date currentdate;
	private Date nextYearDate;
	private Date prevYearDate;
	
	@Before
	public void setUp() throws Exception {
		today = LocalDate.now();
		nextYear = today.plus(1, ChronoUnit.YEARS);
		prevyear = today.minus(1, ChronoUnit.YEARS);
		currentdate = java.sql.Date.valueOf(today);
		nextYearDate = java.sql.Date.valueOf(nextYear);
		prevYearDate = java.sql.Date.valueOf(prevyear);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExpiryFlagUpdate() {
		 Trade t = new Trade("T4", 4, null, nextYearDate);
		 
		Mockito.when(repo.findById("t4")).thenReturn(Optional.of(t));
		 
		t.setMaturityDate(prevYearDate );
		 t.setIsExpired('Y');
		Mockito.when(repo.save(t )).thenReturn(t);
		System.out.println("t.getIsExpired() "+t.getIsExpired());
		assertThat(t.getIsExpired()).isEqualTo('Y');
	}
	
	
 
	
	@Test // (expected =  InvalidTradeVersionException.class)
	
	public void testLesserVersion() throws InvalidTradeVersionException, InterruptedException, ExecutionException {
		Trade t = new Trade("T4", 4, null, nextYearDate);
		 
		Mockito.when(repo.findById("t4")).thenReturn(Optional.of(t));
		 
		t.setVersion(0);
		
		Mockito.when(repo.save(t )).thenReturn(t);
				
		assertThrows(
				InvalidTradeVersionException.class,
	            () -> tradeServiceImpl.save(t));
				 
	}
	
	

}
