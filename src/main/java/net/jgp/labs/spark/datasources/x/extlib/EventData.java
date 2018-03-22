package net.jgp.labs.spark.datasources.x.extlib;

import java.io.Serializable;

public class EventData implements Serializable {
    private static final long serialVersionUID = -2589804417011601051L;
    
    private Float paramA;
    private Float paramB;
    private Float paramC;
    
    // getters
    public Float getParamA() {
        return paramA;
    }
    
    public Float getParamB() {
        return paramB;
    }

    public Float getParamC() {
        return paramC;
    }
    
    // setters
    public void setParamA(Float x) {
        this.paramA = x;
    }
    
    public void setParamB(Float x) {
        this.paramB = x;
    }
    
    public void setParamC(Float x) {
        this.paramC = x;
    }
    
}
