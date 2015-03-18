package com.success.coaqua.entity;

/**
 * Created by phungdinh on 27/02/2015.
 */
public class RequestEmail {

    private int id;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String ewayAuthCode;
    private String ewayTrxnReference;
    private String totalBox;
    private String grandTotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getEwayAuthCode() {
        return ewayAuthCode;
    }

    public void setEwayAuthCode(String ewayAuthCode) {
        this.ewayAuthCode = ewayAuthCode;
    }

    public String getEwayTrxnReference() {
        return ewayTrxnReference;
    }

    public void setEwayTrxnReference(String ewayTrxnReference) {
        this.ewayTrxnReference = ewayTrxnReference;
    }

    public String getTotalBox() {
        return totalBox;
    }

    public void setTotalBox(String totalBox) {
        this.totalBox = totalBox;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }
}
