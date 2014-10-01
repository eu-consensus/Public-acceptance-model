/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author ViP
 */
public class SimplePercentageCorrelation {
    float percentage;
    long clientId;
    String result;

    public String getResult() {
        return result;
    }
    
    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public float getPercentage() {
        return percentage;
    }

    public long getClientId() {
        return clientId;
    }

    public SimplePercentageCorrelation(float percentage, long clientId) {
        this.percentage = percentage;
        this.clientId = clientId;
        result="";
    }

    @Override
    public boolean equals(Object obj) {
        try{ return this.clientId == ((SimplePercentageCorrelation) obj).clientId;}
        catch(Exception ex){return false;}
    }
    
}
