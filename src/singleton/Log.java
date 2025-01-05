package singleton;
import java.util.*;
import java.io.*;

public class Log {
    private static Log instance;
    private StringBuilder strBld = new StringBuilder();

    private Log() {
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public void logEvent(String event) {
        String entry = new Date() + " | " + event + "\n";
        strBld.append(entry);
        // Print to console for debug
        System.out.print(entry);
    }

    public void writeLogToFile(String filePath) {
        try (FileWriter fileWrite = new FileWriter(filePath, false)) {
            fileWrite.write(strBld.toString());
            fileWrite.flush();
            System.out.println("Log written to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}   
