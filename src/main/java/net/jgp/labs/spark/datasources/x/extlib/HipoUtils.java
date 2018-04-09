package net.jgp.labs.spark.datasources.x.extlib;

import org.jlab.io.hipo.*;

import java.util.*;


public class HipoUtils {

    public static List<EventData> processFromFilename(String pathToHipo) {
        List<EventData> events = new ArrayList<>();

        HipoDataSource reader = new HipoDataSource();
        reader.open(pathToHipo);
        int nevents = reader.getSize();
        
        for(int i = 0; i < nevents; i++){
            HipoDataEvent event = (HipoDataEvent) reader.gotoEvent(i);
            HipoDataBank bank_p = (HipoDataBank) event.getBank("mc::event");
            
            for(int k = 0; k < bank_p.rows(); k++){
                int pid = bank_p.getShort("pid", k);
                if (pid == -211 || pid == 2122){    
                    EventData ed = new EventData();
                    //int status = bank_p.getByte("status",k);
                    ed.setPID(pid);
                    /*
                    ed.setPX(bank_p.getFloat("px",k));
                    ed.setPY(bank_p.getFloat("py",k));
                    ed.setPZ(bank_p.getFloat("pz",k));
                    ed.setVX(bank_p.getFloat("vx",k));
                    ed.setVY(bank_p.getFloat("vy",k));
                    ed.setVZ(bank_p.getFloat("vz",k));
                    ed.setStatus(status); */
                    events.add(ed);
                }
            }
        }
        return events;
    }
}
