package controller;

import model.*;
import singleton.*;

public class Worker {
    public void processPackage(Customer c, Parcel p){
        if (p == null) {
            Log.getInstance().logEvent("No Parcel to process.");
            return;
        }
        if (p.isProcessed()) {
            Log.getInstance().logEvent("Parcel no: " + p.getParcelId()
                    + " is already processed.");
        }
        double fee = p.calculateFee();
        p.setProcessed(true);
        Log.getInstance().logEvent(c.getName() + " is processed" + '\n' +
                                   "Parcel: " + p.getParcelId() + '\n' +
                                   "Fee $: " + fee);
    }
}
