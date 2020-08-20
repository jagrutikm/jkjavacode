package com.db.tradestore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Trade {
	@Id	
	private String tradeId;	
	private Integer version;
	private Date createdOn;
	private Date maturityDate;
	private char isExpired;
	
	 
	public Trade() {
		
	}
	
	public Trade(String tradeId, Integer version, Date createdOn, Date maturityDate ) {
		super();
		this.tradeId = tradeId;
		this.version = version;
		this.createdOn = createdOn;
		this.maturityDate = maturityDate;
		 
	}
	
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	public char getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(char isExpired) {
		this.isExpired = isExpired;
	}
	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", version=" + version + ", createdOn=" + createdOn + ", maturityDate="
				+ maturityDate + ", isExpired=" + isExpired + "]";
	}
	 
	 
	 
	 
}
