package com.success.coaqua.entity;

public class TransactionRequest {
	
	private Integer id;
	private String ewayCustomerID;
	private long ewayTotalAmount;
	private String ewayCardHoldersName;
	private String ewayCardNumber;
	private String ewayCardExpiryMonth;
	private String ewayCardExpiryYear;
	private String ewayCVN;
	
	// not require
	private String ewayCustomerFirstName;
	private String ewayCustomerLastName;
	private String ewayCustomerEmail;
	private String ewayCustomerAddress;
	private String ewayCustomerInvoiceDescription;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEwayCustomerID() {
		return ewayCustomerID;
	}
	public void setEwayCustomerID(String ewayCustomerID) {
		this.ewayCustomerID = ewayCustomerID;
	}
	public long getEwayTotalAmount() {
		return ewayTotalAmount;
	}
	public void setEwayTotalAmount(long ewayTotalAmount) {
		this.ewayTotalAmount = ewayTotalAmount;
	}
	public String getEwayCardHoldersName() {
		return ewayCardHoldersName;
	}
	public void setEwayCardHoldersName(String ewayCardHoldersName) {
		this.ewayCardHoldersName = ewayCardHoldersName;
	}
	public String getEwayCardNumber() {
		return ewayCardNumber;
	}
	public void setEwayCardNumber(String ewayCardNumber) {
		this.ewayCardNumber = ewayCardNumber;
	}
	public String getEwayCardExpiryMonth() {
		return ewayCardExpiryMonth;
	}
	public void setEwayCardExpiryMonth(String ewayCardExpiryMonth) {
		this.ewayCardExpiryMonth = ewayCardExpiryMonth;
	}
	public String getEwayCardExpiryYear() {
		return ewayCardExpiryYear;
	}
	public void setEwayCardExpiryYear(String ewayCardExpiryYear) {
		this.ewayCardExpiryYear = ewayCardExpiryYear;
	}
	public String getEwayCVN() {
		return ewayCVN;
	}
	public void setEwayCVN(String ewayCVN) {
		this.ewayCVN = ewayCVN;
	}
	public String getEwayCustomerFirstName() {
		return ewayCustomerFirstName;
	}
	public void setEwayCustomerFirstName(String ewayCustomerFirstName) {
		this.ewayCustomerFirstName = ewayCustomerFirstName;
	}
	public String getEwayCustomerLastName() {
		return ewayCustomerLastName;
	}
	public void setEwayCustomerLastName(String ewayCustomerLastName) {
		this.ewayCustomerLastName = ewayCustomerLastName;
	}
	public String getEwayCustomerEmail() {
		return ewayCustomerEmail;
	}
	public void setEwayCustomerEmail(String ewayCustomerEmail) {
		this.ewayCustomerEmail = ewayCustomerEmail;
	}
	public String getEwayCustomerAddress() {
		return ewayCustomerAddress;
	}
	public void setEwayCustomerAddress(String ewayCustomerAddress) {
		this.ewayCustomerAddress = ewayCustomerAddress;
	}
	public String getEwayCustomerInvoiceDescription() {
		return ewayCustomerInvoiceDescription;
	}
	public void setEwayCustomerInvoiceDescription(
			String ewayCustomerInvoiceDescription) {
		this.ewayCustomerInvoiceDescription = ewayCustomerInvoiceDescription;
	}

}
