package com.orderprioritizer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class GUI extends JPanel {

    private OrderManager orderManager = new OrderManager();
    private JTable table;
    private JTextField txtOrderId = new JTextField();
    private JTextField txtCustomer = new JTextField();
    private JComboBox<String> cmbPriority = new JComboBox<>();

    public GUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245)); // overall background
        buildUI();
        autoLoadToday();
    }

    private void buildUI() {
        // ================= LEFT FORM PANEL =================
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(new Color(250, 250, 255)); // soft light blue
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200),1,true),
                new EmptyBorder(20,20,20,20)
        ));

        // ===== Title =====
        JLabel title = new JLabel("Order Prioritizer");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(title);
        formCard.add(Box.createRigidArea(new Dimension(0,15)));

        // ===== Order ID =====
        JLabel lblOrderId = new JLabel("Order ID");
        lblOrderId.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(lblOrderId);

        txtOrderId.setMaximumSize(new Dimension(250, 30));
        txtOrderId.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtOrderId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtOrderId.setBorder(BorderFactory.createLineBorder(new Color(200,200,200),1,true));
        formCard.add(txtOrderId);
        formCard.add(Box.createRigidArea(new Dimension(0,10)));

        // ===== Customer =====
        JLabel lblCustomer = new JLabel("Customer");
        lblCustomer.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(lblCustomer);

        txtCustomer.setMaximumSize(new Dimension(250, 30));
        txtCustomer.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtCustomer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCustomer.setBorder(BorderFactory.createLineBorder(new Color(200,200,200),1,true));
        formCard.add(txtCustomer);
        formCard.add(Box.createRigidArea(new Dimension(0,10)));

        // ===== Priority =====
        JLabel lblPriority = new JLabel("Priority");
        lblPriority.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(lblPriority);

        cmbPriority.setMaximumSize(new Dimension(250, 30));
        cmbPriority.setAlignmentX(Component.CENTER_ALIGNMENT);
        cmbPriority.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPriority.setBorder(BorderFactory.createLineBorder(new Color(200,200,200),1,true));
        cmbPriority.addItem("High");
        cmbPriority.addItem("Medium");
        cmbPriority.addItem("Low");
        formCard.add(cmbPriority);
        formCard.add(Box.createRigidArea(new Dimension(0,15)));

        // ===== Add Button =====
        JButton btnAdd = new JButton("Tambah Order");
        btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAdd.setBackground(new Color(52, 152, 219));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdd.setBorder(BorderFactory.createEmptyBorder(8,20,8,20));
        btnAdd.addActionListener(e -> addOrder());
        formCard.add(btnAdd);

        // ===== Left Panel =====
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formCard, BorderLayout.NORTH);
        leftPanel.setPreferredSize(new Dimension(320,0));
        leftPanel.setBackground(new Color(245,245,245));

        // ================= CENTER TABLE =================
        String[] columns = {"Order ID", "Customer", "Priority"};
        table = new JTable(new DefaultTableModel(columns,0));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setFont(new Font("Arial",Font.BOLD,14));
        table.setShowGrid(true);
        table.setGridColor(new Color(220,220,220));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220),1,true),
                new EmptyBorder(15,15,15,15)
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.setBorder(new EmptyBorder(20,20,20,20));
        centerPanel.setBackground(new Color(245,245,245));

        // ================= FOOTER =================
        JButton btnLoad = new JButton("Load CSV");
        JButton btnSave = new JButton("Save CSV");
        JButton btnClear = new JButton("Clear Table");

        btnLoad.setBackground(new Color(46, 204, 113));
        btnSave.setBackground(new Color(241, 196, 15));
        btnClear.setBackground(new Color(231, 76, 60));
        btnLoad.setForeground(Color.WHITE);
        btnSave.setForeground(Color.WHITE);
        btnClear.setForeground(Color.WHITE);
        btnLoad.setFocusPainted(false);
        btnSave.setFocusPainted(false);
        btnClear.setFocusPainted(false);

        btnLoad.addActionListener(e -> loadFromCSV());
        btnSave.addActionListener(e -> saveToCSV());
        btnClear.addActionListener(e -> {
            orderManager.getOrders().clear();
            ((DefaultTableModel)table.getModel()).setRowCount(0);
        });

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        footer.add(btnLoad);
        footer.add(btnSave);
        footer.add(btnClear);
        footer.setBackground(new Color(245,245,245));

        // ================= ADD PANELS TO MAIN =================
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    // ================= LOGIC =================
    private void addOrder() {
        String id = txtOrderId.getText().trim();
        String customer = txtCustomer.getText().trim();
        String priority = (String) cmbPriority.getSelectedItem();

        if(id.isEmpty() || customer.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Order ID dan Customer wajib diisi!");
            return;
        }

        for(Order o: orderManager.getOrders()) {
            if(o.getOrderId().equalsIgnoreCase(id)) {
                JOptionPane.showMessageDialog(this,"Order ID sudah ada!");
                return;
            }
        }

        Order order = new Order(id,customer,priority);
        orderManager.addOrder(order);
        ((DefaultTableModel)table.getModel()).addRow(new Object[]{id,customer,priority});

        txtOrderId.setText("");
        txtCustomer.setText("");
        txtOrderId.requestFocus();
    }

    private void loadFromCSV() {
        List<Order> orders = CSVHandler.loadOrders(getTodayFile());
        orderManager.setOrders(orders);
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
        for(Order o: orders) {
            model.addRow(new Object[]{o.getOrderId(),o.getCustomerName(),o.getPriority()});
        }
    }

    private void saveToCSV() {
        if(orderManager.getOrders().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Tidak ada data untuk disimpan!");
            return;
        }
        CSVHandler.saveOrders(getTodayFile(),orderManager.getOrders());
        JOptionPane.showMessageDialog(this,"Data berhasil disimpan");
    }

    private void autoLoadToday() {
        List<Order> orders = CSVHandler.loadOrders(getTodayFile());
        orderManager.setOrders(orders);
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
        for(Order o: orders) {
            model.addRow(new Object[]{o.getOrderId(),o.getCustomerName(),o.getPriority()});
        }
    }

    private String getTodayFile() {
        return "data/orders_" + LocalDate.now() + ".csv";
    }
}
