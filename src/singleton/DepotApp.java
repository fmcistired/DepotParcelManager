package singleton;

import controller.Manager;

public class DepotApp {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.run();
    }
}
