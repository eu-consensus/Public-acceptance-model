/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author ViP
 */
public class TriEdge {
    public String left;
    public String source;
    public String right;
    float weight;

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public TriEdge(String left, String source, String right, float weight) {
        this.left = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(left);
        this.source = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(source);
        this.right = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(right);
        this.weight = weight;
    }

    public TriEdge(String left, String source, String right) {
        this.left = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(left);
        this.source = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(source);
        this.right = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(right);
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
            if(((((TriEdge)obj).source.equals(this.source))&&
                (((TriEdge)obj).right.equals(this.right))&&
                (((TriEdge)obj).left.equals(this.left)))||
               ((((TriEdge)obj).source.equals(this.source))&&
                (((TriEdge)obj).right.equals(this.left))&&
                (((TriEdge)obj).left.equals(this.right)))){
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
        return "{"+this.left+", "+this.source+", "+this.right+", "+this.weight+"}";
    }
    
}
