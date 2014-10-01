/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import twitter4j.Status;

/**
 *
 * @author ViP
 */
public class CustomStatus{
    
    long id;
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

    public CustomStatus(long id, String date, String text, int favoriteCount, int retweetCount) {
        this.id = id;
        this.date = date; 
        this.text = text;
        this.clearText = clear(text);
        this.favoriteCount = favoriteCount;
        this.retweetCount = retweetCount;
        this.polarity = null;
        this.prePolarity = null;
    }
    
    public CustomStatus(Status source) {
        this.id = source.getId();
        this.date = source.getCreatedAt().toString();
        this.text = source.getText();
        this.clearText = clear(text);
        this.favoriteCount = source.getFavoriteCount();
        this.retweetCount = source.getRetweetCount();
        this.polarity = null;
        this.prePolarity = null;
    }
    
    private String clear(String dirty){
        String clear="";
        if(dirty.indexOf("http://")>=0){
            if(dirty.indexOf(" ", dirty.indexOf("http://"))>=0){      
                clear=dirty.toLowerCase().replaceAll("http://[\\S|\\p{Punct}]*", "URL");
            }else{
                clear=dirty.toLowerCase().replaceAll("http://.*", "URL");
            }
        }else{
        clear=dirty.toLowerCase();
        }
        if(clear.indexOf("rt ")>=0){
            clear=clear.replace("rt ", "");
        }
        if(clear.indexOf("#")>=0){
            clear=clear.replaceAll("#","");
        }
        if(clear.indexOf("@")>=0){
            clear=clear.replaceAll("@\\w+", "REF");
            if(clear.indexOf("REF:")>=0){
                clear=clear.replaceAll("REF:", "");
            }
        }
        if(clear.indexOf("\n")>=0){
            clear=clear.replaceAll("\\n", " ");
        }
        if(clear.indexOf("& ")>=0){
            clear=clear.replaceAll("& ", "and ");
        }
        if(clear.indexOf("&")>=0){
            clear=clear.replaceAll("&", "");
        }
        return clear.trim();
    }
    
    @Override
    public String toString(){
        return "id: "+this.id+"\ndate: "+this.date+"\ntext: "+this.text+"\nfavoriteCount: "+this.favoriteCount+"\nretweetCount: "+this.retweetCount;
    }

    public long getId() {
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
