package com.orderprioritizer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {

    // LOAD CSV
    public static List<Order> loadOrders(String path) {
        List<Order> orders = new ArrayList<>();

        File file = new File(path);
        if (!file.exists()) {
            return orders;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    orders.add(new Order(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim()
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // SAVE CSV
    public static void saveOrders(String path, List<Order> orders) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (Order o : orders) {
                pw.println(
                        o.getOrderId() + "," +
                        o.getCustomerName() + "," +
                        o.getPriority()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
