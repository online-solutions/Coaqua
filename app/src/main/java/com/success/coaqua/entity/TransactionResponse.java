package com.success.coaqua.entity;

public class TransactionResponse {
	
	// parent tag = ewayresponse -> not show
	
	private int id;
	private String ewayTrxnStatus;
	private String ewayTrxnNumber;
	private String ewayTrxnReference;
	private String ewayTrxnOption1;
	private String ewayTrxnOption2;
	private String ewayTrxnOption3;
	private String ewayAuthCode;
	private String ewayReturnAmount;
	private String ewayTrxnError;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEwayTrxnStatus() {
		return ewayTrxnStatus;
	}
	public void setEwayTrxnStatus(String ewayTrxnStatus) {
		this.ewayTrxnStatus = ewayTrxnStatus;
	}
	public String getEwayTrxnNumber() {
		return ewayTrxnNumber;
	}
	public void setEwayTrxnNumber(String ewayTrxnNumber) {
		this.ewayTrxnNumber = ewayTrxnNumber;
	}
	public String getEwayTrxnReference() {
		return ewayTrxnReference;
	}
	public void setEwayTrxnReference(String ewayTrxnReference) {
		this.ewayTrxnReference = ewayTrxnReference;
	}
	public String getEwayTrxnOption1() {
		return ewayTrxnOption1;
	}
	public void setEwayTrxnOption1(String ewayTrxnOption1) {
		this.ewayTrxnOption1 = ewayTrxnOption1;
	}
	public String getEwayTrxnOption2() {
		return ewayTrxnOption2;
	}
	public void setEwayTrxnOption2(String ewayTrxnOption2) {
		this.ewayTrxnOption2 = ewayTrxnOption2;
	}
	public String getEwayTrxnOption3() {
		return ewayTrxnOption3;
	}
	public void setEwayTrxnOption3(String ewayTrxnOption3) {
		this.ewayTrxnOption3 = ewayTrxnOption3;
	}
	public String getEwayAuthCode() {
		return ewayAuthCode;
	}
	public void setEwayAuthCode(String ewayAuthCode) {
		this.ewayAuthCode = ewayAuthCode;
	}
	public String getEwayReturnAmount() {
		return ewayReturnAmount;
	}
	public void setEwayReturnAmount(String ewayReturnAmount) {
		this.ewayReturnAmount = ewayReturnAmount;
	}
	public String getEwayTrxnError() {
		return ewayTrxnError;
	}
	public void setEwayTrxnError(String ewayTrxnError) {
		this.ewayTrxnError = ewayTrxnError;
	}
	
	
}
