package com.orderprioritizer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderManager {

    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
        sortOrders();
    }

    public List<Order> getOrders() {
        return orders;
    }

    // INI YANG TADI ERROR → SEKARANG FIX
    public void setOrders(List<Order> orders) {
        this.orders = orders;
        sortOrders();
    }

    private void sortOrders() {
        orders.sort(Comparator.comparingInt(this::priorityValue));
    }

    private int priorityValue(Order order) {
        switch (order.getPriority()) {
            case "High":
                return 1;
            case "Medium":
                return 2;
            case "Low":
                return 3;
            default:
                return 4;
        }
    }
}
