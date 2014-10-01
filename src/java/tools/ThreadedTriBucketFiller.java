/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.TriEdge;
import java.util.ArrayList;

/**
 *
 * @author ViP
 */
public class ThreadedTriBucketFiller extends Thread{
    ArrayList<CallableTriBucket> targets;
    ArrayList<NGramTriGraphCreator> theGrams;

    public ThreadedTriBucketFiller(ArrayList<CallableTriBucket> targets, ArrayList<NGramTriGraphCreator> grams) {
        this.targets = targets;
        this.theGrams = grams;
    }

    @Override
    public void run() {
       for (int i = 0; i < theGrams.size(); i++) {
            for (int j = 0; j < theGrams.get(i).Edges.size(); j++) {
                TriEdge theEdge=theGrams.get(i).Edges.get(j);
                TriEdge smartAddResult=correctIt(theEdge);
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
    
    private TriEdge correctIt(TriEdge ed){
        if(gramToNumber(ed.left)<=gramToNumber(ed.right)){ return ed;}
        else{ return new TriEdge(ed.right,ed.source, ed.left, ed.getWeight());}
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
