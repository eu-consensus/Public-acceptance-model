/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author ViP
 */
public class SimpleValueCorrelation implements Comparable{
    long value;
    int index;

    public long getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
    
    public SimpleValueCorrelation(long value, int index) {
        this.value = value;
        this.index = index;
    }

    @Override
    public int compareTo(Object o) {
        try{
            return (int) (this.value-((SimpleValueCorrelation)o).value);
        }catch(Exception ex){ ex.printStackTrace(); return -1;}
    }
    
    public void slide(int index){
        if(this.index>index){
            this.index--;
        }
    }
}
