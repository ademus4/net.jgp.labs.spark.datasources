package net.jgp.labs.spark.datasources.x.extlib;

import java.io.Serializable;

public class EventData implements Serializable {
    private static final long serialVersionUID = -2589804417011601051L;
    
    private int eventNumber;
    private int pid;
    private float px;
    private float py;
    private float pz;
    private float vx;
    private float vy;
    private float vz;
    private int charge;
    private float beta;
    private float chi2pid;
    private int status;
    
    // getters
    public int getEventNumber() {
        return eventNumber;
    }
    
    public int getPID() {
        return pid;
    }

    public float getPX() {
        return px;
    }
    
    public float getPY() {
        return py;
    }
    
    public float getPZ() {
        return pz;
    }
    
    public float getVX() {
        return vx;
    }
    
    public float getVY() {
        return vy;
    }
    
    public float getVZ() {
        return vz;
    }

    public int getCharge() {
        return charge;
    }

    public float getBeta() {
        return beta;
    }

    public float getChi2pid() {
        return chi2pid;
    }

    public int getStatus() {
        return status;
    }

    // setters
    public void setEventNumber(int eventNumber) {
        this.eventNumber = eventNumber;
    }
    
    public void setPID(int pid) {
        this.pid = pid;
    }

    public void setPX(float px) {
        this.px = px;
    }
    
    public void setPY(float py) {
        this.py = py;
    }
    
    public void setPZ(float pz) {
        this.pz = pz;
    }
    
    public void setVX(float vx) {
        this.vx = vx;
    }
    
    public void setVY(float vy) {
        this.vy = vy;
    }
    
    public void setVZ(float vz) {
        this.vz = vz;
    }
    
    public void setCharge(int charge) {
        this.charge = charge;
    }
    
    public void setBeta(float beta) {
        this.beta = beta;
    }
    
    public void setChi2pid(float chi2pid) {
        this.chi2pid = chi2pid;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
}
