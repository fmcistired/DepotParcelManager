package model;

import singleton.Log;

import java.util.*;

public class QueueOfCustomers {
    private Queue<Customer> customerQueue = new LinkedList<>();


    public void addCustomer(Customer c) {
        customerQueue.add(c);
        Log.getInstance().logEvent("New customer added: " + c);
    }

    public Customer removeCustomer() {
        Customer c = customerQueue.poll();
        if (c != null) {
            Log.getInstance().logEvent("Customer removed: " + c);
        }
        return c;
    }

    public int getQueueSize() {
        return customerQueue.size();
    }

    public Queue<Customer> getCustomerQueue() {
        return customerQueue;
    }
}
