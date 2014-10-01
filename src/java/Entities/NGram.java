/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Silvestro
 */
public class NGram {
    String name;
    int weight;

    public NGram(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
    
    public void reversePolarity(){
        this.weight*=-1;
    }
    
    public void zeroPolarity() {
        this.weight*=0;
    }
    
    public void add(int weight){
        this.weight += weight;
    }
    
    public void increase(){
        this.weight ++;
    }
    
    public void decrease(){
        this.weight --;
    }

    @Override
    public boolean equals(Object obj) {
        try{
            if(((NGram)obj).name.equals(this.name)){
                return true;
            }else{
                return false;
            }
        }catch(Exception ex){
            return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public String toString() {
        return "{"+this.name+", "+this.weight+"}";
    }
    
    
    
}
