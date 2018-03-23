package net.jgp.labs.spark.datasources.x.extlib;

import org.jlab.io.hipo.*;
import org.jlab.groot.data.*;
import org.jlab.groot.ui.*;

import java.util.*;


public class HipoUtils {

    public static List<EventData> processFromFilename(String pathToHipo) {
        List<EventData> events = new ArrayList<>();

        HipoDataSource reader = new HipoDataSource();
        reader.open(pathToHipo);
        int nevents = reader.getSize();
        
        for(int i = 0; i < nevents; i++){
            HipoDataEvent event = (HipoDataEvent) reader.gotoEvent(i);
            HipoDataBank bank_p = (HipoDataBank) event.getBank("REC::Particle");
            HipoDataBank bank_i = (HipoDataBank) event.getBank("REC::Event");
            int event_n = bank_i.getInt("NEVENT",0);
            
            for(int k = 0; k < bank_p.rows(); k++){
                EventData ed = new EventData();
                ed.setEventNumber(event_n);
                ed.setPID(bank_p.getInt("pid",k));
                
                int charge = bank_p.getByte("charge",k);
                ed.setCharge(charge);
                events.add(ed);
            }
        }
        return events;
    }
}
