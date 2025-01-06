package controller;

import model.*;
import singleton.Log;
import view.MainView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Manager {
    private QueueOfCustomers queueOfCustomers;
    private ParcelMap parcelMap;
    private Worker worker;

    // CSV files containing Customer & Parcel data
    private static final String CUSTOMERS = "Custs.csv";
    private static final String PARCELS = "Parcels.csv";

    public Manager() {
        queueOfCustomers = new QueueOfCustomers();
        parcelMap = new ParcelMap();
        worker = new Worker();
    }

    public void run() {
        // Load CSV file data
        loadData();

        // Create GUI
        MainView gui = new MainView(queueOfCustomers, parcelMap, worker);
        gui.setVisible(true);

        // Initial log entry
        Log.getInstance().logEvent("Depot Application Started.");
    }

    // Method to load the Customer & Parcel data
    private void loadData() {
        loadCustomers();
        loadParcels();
    }

    private void loadCustomers() {
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMERS))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Expected format: ID,Name
                String[] fields = line.split(",");
                if (fields.length >= 2) {
                    String id = fields[0].trim();
                    String name = fields[1].trim();
                    queueOfCustomers.addCustomer(new Customer(id, name));
                } else {
                    Log.getInstance().logEvent("Invalid customer entry skipped: " + line);
                }
            }
            Log.getInstance().logEvent("Customers successfully loaded from the file: " + CUSTOMERS);
        } catch (IOException e) {
            Log.getInstance().logEvent("Error loading customers from file: " + e.getMessage());
            System.err.println("Error loading customers from file: " + e.getMessage());
        }
    }

    private void loadParcels() {
        try (BufferedReader br = new BufferedReader(new FileReader(PARCELS))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Expected format: TrackingNo,Weight,Dim1,Dim2,Dim3
                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    String trackingNo = fields[0].trim();
                    int daysIn = Integer.parseInt(fields[1].trim());
                    double weight = Double.parseDouble(fields[2].trim());
                    String dimensions = String.join("x", fields[3].trim(), fields[4].trim(), fields[5].trim());

                    parcelMap.addParcel(trackingNo, new Parcel(trackingNo, daysIn, weight, dimensions));
                } else {
                    Log.getInstance().logEvent("Invalid parcel entry skipped: " + line);
                }
            }
            Log.getInstance().logEvent("Parcels successfully loaded from the file: " + PARCELS);
        } catch (IOException e) {
            Log.getInstance().logEvent("Error loading parcels from file: " + e.getMessage());
            System.err.println("Error loading parcels from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            Log.getInstance().logEvent("Error parsing parcel data: " + e.getMessage());
            System.err.println("Error parsing parcel data: " + e.getMessage());
        }
    }
}
