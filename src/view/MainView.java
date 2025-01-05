package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    // Constructor
    public MainView(){
        setTitle("Depot Parcel Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeComponent();
    }

    private void initializeComponent() {
        // Parcel list panel
        JPanel parcelPanel = new JPanel();
        parcelPanel.setBorder(BorderFactory.createTitledBorder("Parcels"));
        parcelPanel.setLayout(new BorderLayout());
        JTextArea parcelsArea = new JTextArea();
        parcelsArea.setEditable(false);
        JScrollPane parcelsScroll = new JScrollPane(parcelsArea);
        parcelPanel.add(parcelsScroll, BorderLayout.CENTER);

        // Customer queue panel
        JPanel queuePanel = new JPanel();
        queuePanel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        queuePanel.setLayout(new BorderLayout());
        JTextArea queueArea = new JTextArea();
        queueArea.setEditable(false);
        JScrollPane queueScroll = new JScrollPane(queueArea);
        queuePanel.add(queueScroll, BorderLayout.CENTER);

        // Current processing details panel
        JPanel processingPanel = new JPanel();
        processingPanel.setBorder(BorderFactory.createTitledBorder("Processing Details"));
        processingPanel.setLayout(new GridLayout(3, 2));
        processingPanel.add(new JLabel("Customer:"));
        JTextField customerField = new JTextField();
        customerField.setEditable(false);
        processingPanel.add(customerField);
        processingPanel.add(new JLabel("Parcel:"));
        JTextField parcelField = new JTextField();
        parcelField.setEditable(false);
        processingPanel.add(parcelField);
        processingPanel.add(new JLabel("Fee:"));
        JTextField feeField = new JTextField();
        feeField.setEditable(false);
        processingPanel.add(feeField);

        // Add panels to the main layout
        add(parcelPanel, BorderLayout.WEST);
        add(queuePanel, BorderLayout.EAST);
        add(processingPanel, BorderLayout.SOUTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton processButton = new JButton("Process Next Customer");
        JButton logButton = new JButton("View Log");
        buttonPanel.add(processButton);
        buttonPanel.add(logButton);

        // Add button panel to the bottom
        add(buttonPanel, BorderLayout.NORTH);

    }

    public void display(){
        setVisible(true);
    }

}
