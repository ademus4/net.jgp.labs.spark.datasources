package net.jgp.labs.spark.datasources.x.extlib;

import java.io.Serializable;

public class EventData implements Serializable {
    private static final long serialVersionUID = -2589804417011601051L;
    
    private int eventNumber;
    private int pid;
    private int charge;
    
    // getters
    public int getEventNumber() {
        return eventNumber;
    }
    
    public int getPID() {
        return pid;
    }

    public int getCharge() {
        return charge;
    }
    
    // setters
    public void setEventNumber(int x) {
        this.eventNumber = x;
    }
    
    public void setPID(int x) {
        this.pid = x;
    }
    
    public void setCharge(int x) {
        this.charge = x;
    }
    
}
