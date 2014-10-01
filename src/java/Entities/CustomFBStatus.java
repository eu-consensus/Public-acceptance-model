/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author ViP
 */
public class CustomFBStatus {
    String id;
    String date;
    String text;
    String clearText;
    int favoriteCount;
    int retweetCount;
    Double polarity;
    Integer prePolarity;

    public String getClearText() {
        return clearText;
    }

    public CustomFBStatus(String id, String date, String text, int favoriteCount, int retweetCount) {
        this.id = id;
        this.date = date; 
        this.text = text;
        this.clearText = clear(text);
        this.favoriteCount = favoriteCount;
        this.retweetCount = retweetCount;
        this.polarity = null;
        this.prePolarity = null;
    }
    
    private String clear(String dirty){
        if(dirty!=null){
        String clear="";
        if(dirty.indexOf("\r")>=0){
            clear=dirty.toLowerCase().replaceAll("\r", " ");
        }
        if(dirty.indexOf("\n")>=0){
            clear=dirty.replaceAll("\n", " ");
        }
        if(dirty.indexOf("\t")>=0){
            clear=dirty.replaceAll("\t", " ");
        }
        if(dirty.indexOf("http://")>=0){
            if(dirty.indexOf(" ", dirty.indexOf("http://"))>=0){      
                clear=dirty.replaceAll("http://[\\S|\\p{Punct}]*", "URL");
            }else{
                clear=dirty.replaceAll("http://.*", "URL");
            }
        }else{
        clear=dirty.toLowerCase();
        }
        if(clear.indexOf("#")>=0){
            clear=clear.replaceAll("#","");
        }
        if(clear.indexOf("@")>=0){
            clear=clear.replaceAll("@","");
        }
        return clear.trim();
        }else return "";
    }
    
    @Override
    public String toString(){
        return "id: "+this.id+"\ndate: "+this.date+"\ntext: "+this.text+"\nfavoriteCount: "+this.favoriteCount+"\nretweetCount: "+this.retweetCount;
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public Double getPolarity() {
        return polarity;
    }

    public void setPolarity(Double polarity) {
        this.polarity = polarity;
    }

    public Integer getPrePolarity() {
        return prePolarity;
    }

    public void setPrePolarity(Integer prePolarity) {
        this.prePolarity = prePolarity;
    }
}
