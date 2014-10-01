/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.Edge;
import java.util.ArrayList;

/**
 *
 * @author ViP
 */
public class ThreadedBucketFiller extends Thread{
    ArrayList<CallableBucket> targets;
    ArrayList<NGramCreator> theGrams;

    public ThreadedBucketFiller(ArrayList<CallableBucket> targets, ArrayList<NGramCreator> grams) {
        this.targets = targets;
        this.theGrams = grams;
    }

    @Override
    public void run() {
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
