package net.jgp.labs.spark.datasources.x.extlib;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CSVUtils {

    public static List<EventData> processFromFilename(String pathToCSV) {
        List<EventData> events = new ArrayList<>();
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(pathToCSV))) {
            while ((line = br.readLine()) != null) {
                String[] row = line.split(cvsSplitBy);
                EventData event = new EventData();
                event.setParamA(Float.parseFloat(row[0]));
                event.setParamB(Float.parseFloat(row[1]));
                event.setParamC(Float.parseFloat(row[2]));
                events.add(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }
}
