package model;

public class Parcel {
    private String parcelId;
    private int daysIn;
    private double weight;
    private String dimensions;
    private boolean isProcessed;

    // Constructor
    public Parcel(String parcelId, int daysIn, double weight, String dimensions) {
        this.parcelId = parcelId;
        this.daysIn = daysIn;
        this.weight = weight;
        this.dimensions = dimensions;
        this.isProcessed = false;

    }

    // Calculation method
    public double calculateFee() {
        // Example: base fee + additional cost per kg
        double baseFee = 5.0;
        double additionalCostPerKg = 2.0;
        double deliveryFree = baseFee + (weight * additionalCostPerKg);

        return deliveryFree;
    }

    // Getters and Setters
    public String getParcelId() {
        return parcelId;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    @Override
    public String toString() {
        return  "Parcel ==========" + '\n' +
                "Tracking Number: " + parcelId + '\n' +
                "Weight: " + weight + '\n' +
                "Processed: " + isProcessed + '\n';

    }
}


