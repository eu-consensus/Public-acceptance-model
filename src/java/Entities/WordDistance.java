/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author ViP
 */
public class WordDistance {
    String WordA;
    String WordB;
    double distance;

    public WordDistance(String WordA, String WordB, double distance) {
        this.WordA = WordA;
        this.WordB = WordB;
        this.distance = distance;
    }

    public String getWordA() {
        return WordA;
    }

    public String getWordB() {
        return WordB;
    }

    public double getDistance() {
        return distance;
    }
    
    @Override
    public String toString(){
        return this.WordA+" - "+this.WordB+" : "+this.distance;
    }
}
