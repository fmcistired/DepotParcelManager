package model;

import singleton.Log;

import java.util.*;

public class ParcelMap {
    // Map to store parcel ID and Parcel object
    private Map<String, Parcel> parcelMap;

    // Constructor
    public ParcelMap() {
        parcelMap = new HashMap<>();
    }

    // Method to add a parcel
    public void addParcel(String id, Parcel parcel) {
        if (parcelMap.containsKey(id)) {
            Log.getInstance().logEvent("Parcel with ID " + id + " already exists.");

        } else {
            parcelMap.put(id, parcel);
            Log.getInstance().logEvent("Parcel added: " + parcel);
        }
    }

    // Method to retrieve a parcel by ID
    public Parcel getParcel(String parcelId) {
        return parcelMap.get(parcelId);
    }

    // Method to remove a parcel by ID
    public void removeParcel(String id) {
        if (parcelMap.containsKey(id)) {
            parcelMap.remove(id);
            Log.getInstance().logEvent("Parcel with ID " + id + " removed.");
        } else {
            Log.getInstance().logEvent("Parcel with ID " + id + " does not exist.");
        }
    }

    public List<Parcel> getSortedParcelsByWeight() {
        return parcelMap.values().stream()
                .sorted(Comparator.comparingDouble(Parcel::getWeight))
                .toList();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Parcel parcel : parcelMap.values()) {
            sb.append(parcel.toString()).append("\n");
        }
        return sb.toString();
    }

}

