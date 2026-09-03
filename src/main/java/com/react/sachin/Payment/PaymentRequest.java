package com.react.sachin.Payment;

public class PaymentRequest {
    private String orderId;
    private String amount;
    private String username;

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}