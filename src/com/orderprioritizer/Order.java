package com.orderprioritizer;

public class Order {
    private String orderId;
    private String customerName;
    private String priority;

    public Order(String orderId, String customerName, String priority) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.priority = priority;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPriority() {
        return priority;
    }
}
