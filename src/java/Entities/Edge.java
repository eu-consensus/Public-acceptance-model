/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Silvestro
 */
public class Edge {
    public String source;
    public String target;
    float weight;

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public Edge(String source, String target, float weight) {
        this.source = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(source);
        this.target = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(target);
        this.weight = weight;
    }

    public Edge(String source, String target) {
        this.source = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(source);
        this.target = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(target);
        this.weight = 1;
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
            if(((((Edge)obj).source.equals(this.source))&&(((Edge)obj).target.equals(this.target)))||((((Edge)obj).source.equals(this.target))&&(((Edge)obj).target.equals(this.source)))){
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
        return "{"+this.source+", "+this.target+", "+this.weight+"}";
    }
    
}
