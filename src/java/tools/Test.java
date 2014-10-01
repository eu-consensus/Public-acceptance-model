/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Crawlers.FacebookCrawler;
import Entities.Edge;
import java.util.ArrayList;
import main_src.NGramTests;

/**
 *
 * @author ViP
 */
public class Test {
     public static void main(String[] args) {
        FacebookCrawler fb=new FacebookCrawler();
        fb.search("biodiesel");
     }
    ArrayList<CallableBucket> targets;
    ArrayList<NGramCreator> theGrams;
    public static long low0;
    public static long low1;
    public static long low2;
    public static long low3;
    public static long high0;
    public static long high1;
    public static long high2;
    public static long high3;

    public Test(ArrayList<CallableBucket> targets, ArrayList<NGramCreator> grams) {
        this.targets = targets;
        this.theGrams = grams;
        low0=-1;
        low1=-1;
        low2=-1;
        low3=-1;
        high1=-1;
        high2=-1;
        high3=-1;
        high0=-1;
    }
    
     public void pun() {
        for (int i = 0; i < theGrams.size(); i++) {
            for (int j = 0; j < theGrams.get(i).Edges.size(); j++) {
                Edge theEdge=theGrams.get(i).Edges.get(j);
                Edge smartAddResult=correctIt(theEdge);
                smartAddResult=targets.get(0).smartAdd(smartAddResult,1);
            }
        }
    }
    
    private long gramToNumber(String gram){
        long num=0;
        char[] chars=gram.toCharArray();
        for (int i = chars.length; i > 0; i--) {
            num+=chars[i-1]*Math.pow(10,(chars.length-i)*3);
        }
        return num;
    }
    
    private long getLowest(long num1, long num2){
        if(num1<=num2) return num1;
        else return num2;
    }
    
    private Edge correctIt(Edge ed){
        if(gramToNumber(ed.source)<=gramToNumber(ed.target)){ return ed;}
        else{ return new Edge(ed.target,ed.source,ed.getWeight());}
    }
    
    private int findBucketIndex(long num){
        for (int i = 0; i < targets.size(); i++) {
            if(num<targets.get(i).upperLimit){
                return i;
            }else if(targets.get(i).upperLimit<0){
                return i;
            }
        }
        return targets.size()-1;
    }
}
