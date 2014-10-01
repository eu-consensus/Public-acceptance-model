/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.TriEdge;
import Entities.SimpleValueCorrelation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author ViP
 */
public class CallableTriBucket implements Callable<TriGraphMerger>{
    
    ArrayList<TriEdge> data;
    int serial;
    long lowLimit;
    long upperLimit;
    int sizeLimit;
    boolean last;
    CallableTriBucket nextBucket;
    ArrayList<SimpleValueCorrelation> cors;
    

    public CallableTriBucket(ArrayList<TriEdge> data, int serial,int lowLimit, int upperLimit, int sizeLimit, boolean last) {
        this.data = data;
        this.serial = serial;
        this.lowLimit = lowLimit;
        this.upperLimit = upperLimit;
        this.sizeLimit = sizeLimit;
        this.last = last;
        this.nextBucket = null;
        cors = new ArrayList<SimpleValueCorrelation>();
    }

    public void setNextBucket(CallableTriBucket nextBucket) {
        this.nextBucket = nextBucket;
    }

    @Override
    public TriGraphMerger call() throws Exception {
        TriGraphMerger merger=new TriGraphMerger();
        merger.mergeSimple(data);
        return merger;
    }
    
    public TriEdge smartAdd(TriEdge ed,int direction) {
        long newValue=edgeToNumber(ed);
        if(this.data.size()<this.sizeLimit){
            if(newValue<this.upperLimit || this.upperLimit<0 || this.last){
                this.data.add(ed);
                cors.add(new SimpleValueCorrelation(newValue, this.data.size()-1));
                Collections.sort((List)cors);
                if(lowLimit>newValue || lowLimit<0){
                    lowLimit=newValue;
                }
                if(upperLimit<0){
                    upperLimit=newValue;
                }
            }else{
                if(nextBucket.lowLimit>newValue || nextBucket.lowLimit<0){
                    this.data.add(ed);
                    cors.add(new SimpleValueCorrelation(newValue, this.data.size()-1));
                    Collections.sort((List)cors);
                    this.upperLimit=newValue;
                }else{
                    TriEdge drip=nextBucket.popLower();
                    this.data.add(drip);
                    cors.add(new SimpleValueCorrelation(edgeToNumber(drip), this.data.size()-1));
                    Collections.sort((List)cors);
                    this.upperLimit=edgeToNumber(drip);
                    nextBucket.smartAdd(ed, direction);
                }
            }
        }
        else{
            if(newValue<=this.upperLimit){
                nextBucket.smartAdd(this.popUpper(), direction);
                this.data.add(ed);
                cors.add(new SimpleValueCorrelation(newValue, this.data.size()-1));
                Collections.sort((List)cors);
                if(lowLimit>newValue || lowLimit<0){
                    lowLimit=newValue;
                }
            }else{
                if(!this.last){
                    nextBucket.smartAdd(ed, direction);
                }else{
                    System.out.println("----------------------------------------------------------------------------------------------------------");
                }
            }
        }
        return null;
    }

    /*public Edge smartAdd(Edge ed,int direction) {
        if(data.size()<sizeLimit){
            this.data.add(ed);
            long newLimit = edgeToNumber(ed);
            cors.add(new SimpleValueCorrelation(newLimit, this.data.size()-1));
            Collections.sort((List)cors);
            if(newLimit>upperLimit){
                upperLimit=newLimit;
            }
            if(lowLimit>newLimit || lowLimit<0){
                lowLimit=newLimit;
            }
            //System.out.println(this.serial+" - "+newLimit+", "+(this.data.size()-1)+"\nLow="+lowLimit+"\nUpper="+upperLimit+"\nReturn Val= "+null);
            return null;
        }else{
             long newLimit = edgeToNumber(ed);
             if(this.last){
                Edge old=popLower();
                this.data.add(ed);
                cors.add(new SimpleValueCorrelation(newLimit, this.data.size()-1));
                Collections.sort((List)cors);
                if(newLimit>upperLimit) upperLimit=newLimit;
                //System.out.println(this.serial+" - "+newLimit+", "+(this.data.size()-1)+"\nLow="+lowLimit+"\nUpper="+upperLimit+"\nReturn Val= "+old.toString());
                return old;
             }else{
                Edge old;
                if(direction>0)old=popUpper();
                else old=popLower();
                this.data.add(ed);
                cors.add(new SimpleValueCorrelation(newLimit, this.data.size()-1));
                Collections.sort((List)cors);
                if(newLimit<lowLimit) lowLimit=newLimit;
                //System.out.println(this.serial+" - "+newLimit+", "+(this.data.size()-1)+"\nLow="+lowLimit+"\nUpper="+upperLimit+"\nReturn Val= "+old.toString());
                return old; 
             }
        }
    }*/
    
    private long edgeToNumber(TriEdge ed){
        long num=0;
        char[] chars=ed.source.toCharArray();
        for (int i = chars.length; i > 0; i--) {
            num+=chars[i-1]*Math.pow(10,(chars.length-i)*3);
        }
        return num;
    }
    
    public TriEdge popLower(){
        int index=cors.get(0).getIndex();
        TriEdge old=data.get(index);
        data.remove(index);
        cors.remove(0);
        for (int i = 0; i < cors.size(); i++) {
            cors.get(i).slide(index);
        }
        lowLimit=cors.get(0).getValue();
        return old;
    }
    
    public TriEdge popUpper(){
        int index=cors.get(cors.size()-1).getIndex();
        TriEdge old=data.get(index);
        data.remove(index);
        cors.remove(cors.size()-1);
        for (int i = 0; i < cors.size(); i++) {
            cors.get(i).slide(index);
        }
        upperLimit=cors.get(cors.size()-1).getValue();
        return old;
    }
}
