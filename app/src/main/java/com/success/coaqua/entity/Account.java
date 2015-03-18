package com.success.coaqua.entity;

public class Account {
	
	private int id;
	private String cardNumber;
	private String expireDate;
	private String nameOnCard;
	private String digits;
	
	public Account(){
	}
	
	public Account(String cardNumber, String expireDate, String nameOnCard, String digits){
		this.cardNumber = cardNumber;
		this.expireDate = expireDate;
		this.nameOnCard = nameOnCard;
		this.digits = digits;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	public String getDigits() {
		return digits;
	}
	public void setDigits(String digits) {
		this.digits = digits;
	}

}
