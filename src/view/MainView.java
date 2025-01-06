package view;

import controller.Worker;
import model.*;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private QueueOfCustomers queueOfCustomers;
    private ParcelMap parcelMap;
    private Worker worker;

    private DefaultListModel<String> parcelListModel;
    private JList<String> parcelList;
    private DefaultListModel<String> customerListModel;
    private JList<String> customerList;

    private JTextField customerField;
    private JTextField parcelField;
    private JTextField feeField;

    public MainView(QueueOfCustomers queueOfCustomers, ParcelMap parcelMap, Worker worker) {
        this.queueOfCustomers = queueOfCustomers;
        this.parcelMap = parcelMap;
        this.worker = worker;

        setTitle("Depot Parcel Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeComponent();
    }

    private void initializeComponent() {
        // Initialize Models and Lists
        parcelListModel = new DefaultListModel<>();
        parcelList = new JList<>(parcelListModel);
        customerListModel = new DefaultListModel<>();
        customerList = new JList<>(customerListModel);

        // Parcel Panel
        JPanel parcelPanel = new JPanel(new BorderLayout());
        parcelPanel.setBorder(BorderFactory.createTitledBorder("Parcels"));
        JScrollPane parcelScrollPane = new JScrollPane(parcelList);
        parcelPanel.add(parcelScrollPane, BorderLayout.CENTER);

        // Customer Queue Panel
        JPanel queuePanel = new JPanel(new BorderLayout());
        queuePanel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        JScrollPane customerScrollPane = new JScrollPane(customerList);
        queuePanel.add(customerScrollPane, BorderLayout.CENTER);

        // Processing Details Panel
        JPanel processingPanel = new JPanel(new GridLayout(3, 2));
        processingPanel.setBorder(BorderFactory.createTitledBorder("Processing Details"));
        processingPanel.add(new JLabel("Customer:"));
        customerField = new JTextField();
        customerField.setEditable(false);
        processingPanel.add(customerField);
        processingPanel.add(new JLabel("Parcel:"));
        parcelField = new JTextField();
        parcelField.setEditable(false);
        processingPanel.add(parcelField);
        processingPanel.add(new JLabel("Fee:"));
        feeField = new JTextField();
        feeField.setEditable(false);
        processingPanel.add(feeField);

        // Combine Parcel and Queue Panels with SplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.add(parcelPanel);
        leftPanel.add(queuePanel);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(processingPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton processButton = new JButton("Process Next Customer");
        processButton.addActionListener(e -> processNextCustomer());
        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(e -> addCustomer());
        JButton saveLogButton = new JButton("Save Log");
        saveLogButton.addActionListener(e -> saveLog());
        buttonPanel.add(processButton);
        buttonPanel.add(addCustomerButton);
        buttonPanel.add(saveLogButton);

        // Add Components to Frame
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initial View Update
        updateView();
    }

    private void processNextCustomer() {
        if (queueOfCustomers.getQueueSize() == 0) {
            JOptionPane.showMessageDialog(this, "No customers left in the queue.");
            return;
        }

        Customer customer = queueOfCustomers.removeCustomer();
        if (customer == null) {
            JOptionPane.showMessageDialog(this, "No customers in the queue.");
            return;
        }

        Parcel parcel = parcelMap.getParcel(customer.getId());
        if (parcel == null) {
            JOptionPane.showMessageDialog(this, "No parcel found for customer: " + customer.getName());
            return;
        }

        worker.processPackage(customer, parcel);
        updateView();
    }

    private void addCustomer() {
        String id = JOptionPane.showInputDialog(this, "Enter Customer ID:");
        String name = JOptionPane.showInputDialog(this, "Enter Customer Name:");
        if (id != null && name != null && !id.trim().isEmpty() && !name.trim().isEmpty()) {
            Customer customer = new Customer(id, name);
            queueOfCustomers.addCustomer(customer);
            updateView();
        }
    }

    private void saveLog() {
        String logFilePath = "depot_log.txt";
        singleton.Log.getInstance().writeLogToFile(logFilePath);
        JOptionPane.showMessageDialog(this, "Log saved to " + logFilePath);
    }

    private void updateView() {
        // Update Parcel List
        parcelListModel.clear();
        for (Parcel parcel : parcelMap.getSortedParcelsByWeight()) {
            parcelListModel.addElement(parcel.toString());
        }

        // Update Customer List
        customerListModel.clear();
        for (Customer customer : queueOfCustomers.getCustomerQueue()) {
            customerListModel.addElement(customer.toString());
        }

        // Update Current Processing Details
        Customer currentCustomer = queueOfCustomers.getCustomerQueue().peek();
        if (currentCustomer != null) {
            customerField.setText(currentCustomer.getName());
            Parcel parcel = parcelMap.getParcel(currentCustomer.getId());
            parcelField.setText(parcel != null ? parcel.getParcelId() : "N/A");
            feeField.setText(parcel != null ? String.valueOf(parcel.calculateFee()) : "N/A");
        } else {
            customerField.setText("N/A");
            parcelField.setText("N/A");
            feeField.setText("N/A");
        }
    }
}



