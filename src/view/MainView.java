package view;

import controller.Worker;
import model.*;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private QueueOfCustomers queueOfCustomers;
    private ParcelMap parcelMap;
    private Worker worker;
    private JFrame frame;

    private JTextArea parcelsArea;
    private JTextArea queueArea;
    private JTextField customerField;
    private JTextField parcelField;
    private JTextField feeField;

    // Constructor to Initialize Components
    public MainView(QueueOfCustomers queueOfCustomers, ParcelMap parcelMap, Worker worker) {
        this.queueOfCustomers = queueOfCustomers;
        this.parcelMap = parcelMap;
        this.worker = worker;

        setTitle("Depot Parcel Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeComponent();
    }

    // Initialize Components
    private void initializeComponent() {
        // Parcel list panel
        JPanel parcelPanel = new JPanel();
        parcelPanel.setBorder(BorderFactory.createTitledBorder("Parcels"));
        parcelPanel.setLayout(new BorderLayout());
        parcelsArea = new JTextArea();
        parcelsArea.setEditable(false);
        JScrollPane parcelsScroll = new JScrollPane(parcelsArea);
        parcelPanel.add(parcelsScroll, BorderLayout.CENTER);

        // Customer queue panel
        JPanel queuePanel = new JPanel();
        queuePanel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        queuePanel.setLayout(new BorderLayout());
        queueArea = new JTextArea();
        queueArea.setEditable(false);
        JScrollPane queueScroll = new JScrollPane(queueArea);
        queuePanel.add(queueScroll, BorderLayout.CENTER);

        // Current processing details panel
        JPanel processingPanel = new JPanel();
        processingPanel.setBorder(BorderFactory.createTitledBorder("Processing Details"));
        processingPanel.setLayout(new GridLayout(3, 2));
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

        // Add panels to the layout
        add(parcelPanel, BorderLayout.WEST);
        add(queuePanel, BorderLayout.EAST);
        add(processingPanel, BorderLayout.SOUTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton processButton = new JButton("Process Next Customer");
        JButton logButton = new JButton("Save Log");

        processButton.addActionListener(e -> processNextCustomer());
        logButton.addActionListener(e -> saveLog());

        buttonPanel.add(processButton);
        buttonPanel.add(logButton);
        add(buttonPanel, BorderLayout.NORTH);
    }

    // Action Methods
    private void processNextCustomer() {
        Customer customer = queueOfCustomers.removeCustomer();
        Parcel parcel = parcelMap.getParcel(customer.getId()); // Assuming customer ID matches parcel ID
        worker.processPackage(customer, parcel);
        updateView();
    }

    private void saveLog() {
        String logFilePath = "depot_log.txt";
        singleton.Log.getInstance().writeLogToFile(logFilePath);
        JOptionPane.showMessageDialog(this, "Log saved to " + logFilePath);
    }

    // Update view to refresh GUI Components
    private void updateView() {
        // Update parcel list
        parcelsArea.setText(parcelMap.toString()); // Ensure ParcelMap has a meaningful toString implementation

        // Update customer queue
        StringBuilder queueText = new StringBuilder();
        for (Customer customer : queueOfCustomers.getCustomerQueue()) {
            queueText.append(customer.toString()).append("\n");
        }
        queueArea.setText(queueText.toString());

        // Update current processing details
        Customer currentCustomer = queueOfCustomers.getCustomerQueue().peek();
        if (currentCustomer != null) {
            customerField.setText(currentCustomer.getName());
            Parcel parcel = parcelMap.getParcel(currentCustomer.getId());
            parcelField.setText(parcel != null ? parcel.toString() : "N/A");
            feeField.setText(parcel != null ? String.valueOf(parcel.calculateFee()) : "N/A");
        } else {
            customerField.setText("N/A");
            parcelField.setText("N/A");
            feeField.setText("N/A");
        }
    }

}
