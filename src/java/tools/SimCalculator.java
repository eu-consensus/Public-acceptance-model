/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.Edge;
import Entities.TriEdge;
import java.util.ArrayList;
import main_src.NGramTests;
import main_src.NGramTripleGraphTests;

/**
 *
 * @author Silvestro
 */
public class SimCalculator {
    
    public static void allCalc(String title, ArrayList<Edge> G1){
        System.out.println("----------------------------------------------------------");
        System.out.println(title);
        System.out.println("CSimP= "+SimCalculator.CSim(G1, NGramTests.pos.TotalGraph));
        System.out.println("VSimP= "+SimCalculator.VSim(G1, NGramTests.pos.TotalGraph));
        System.out.println("NVSimP= "+SimCalculator.NVSim(G1, NGramTests.pos.TotalGraph));
        System.out.println("CSimNu= "+SimCalculator.CSim(G1, NGramTests.neu.TotalGraph));
        System.out.println("VSimNu= "+SimCalculator.VSim(G1, NGramTests.neu.TotalGraph));
        System.out.println("NVSimNu= "+SimCalculator.NVSim(G1, NGramTests.neu.TotalGraph));
        System.out.println("CSimN= "+SimCalculator.CSim(G1, NGramTests.neg.TotalGraph));
        System.out.println("VSimN= "+SimCalculator.VSim(G1, NGramTests.neg.TotalGraph));
        System.out.println("NVSimN= "+SimCalculator.NVSim(G1, NGramTests.neg.TotalGraph));
    }
    
    public static void printVector(ArrayList<Float> vector){
        System.out.println("---------------------------------------------------------");
        System.out.print("{ ");
        int i;
        for(i=0;i<vector.size()-1;i++){
            System.out.print(vector.get(i)+", ");
        }
        System.out.print(vector.get(i)+" }\n");
    }
    
    public static ArrayList<Float> calcVectorFast(ArrayList<Edge> G1){
        ArrayList<Float> vector=new ArrayList<Float>();
        vector.add(SimCalculator.CSim(G1, NGramTests.pos.TotalGraph));
        try{
        vector.add(SimCalculator.CSim(G1, NGramTests.neu.TotalGraph));
        }catch(Exception ex){vector.add((float)0.0);}
        vector.add(SimCalculator.CSim(G1, NGramTests.neg.TotalGraph));
        return vector;
    }
    
    public static ArrayList<Float> calcVector(ArrayList<Edge> G1){
        ArrayList<Float> vector=new ArrayList<Float>();
        System.out.println("Starting Vector Calculations...");
        vector.add(SimCalculator.CSim(G1, NGramTests.pos.TotalGraph));
        System.out.println("CSim pos calculated.");
        try{
        vector.add(SimCalculator.CSim(G1, NGramTests.neu.TotalGraph));
        System.out.println("CSim neu calculated.");
        }catch(Exception ex){vector.add((float)0.0);}
        vector.add(SimCalculator.CSim(G1, NGramTests.neg.TotalGraph));
        System.out.println("CSim neg calculated.");
        vector.add(SimCalculator.VSim(G1, NGramTests.pos.TotalGraph));
        System.out.println("VSim pos calculated.");
        try{
        vector.add(SimCalculator.VSim(G1, NGramTests.neu.TotalGraph));
        System.out.println("VSim neu calculated.");
        }catch(Exception ex){vector.add((float)0.0);}
        vector.add(SimCalculator.VSim(G1, NGramTests.neg.TotalGraph));
        System.out.println("VSim neg calculated.");
        vector.add(SimCalculator.NVSim(G1, NGramTests.pos.TotalGraph));
        System.out.println("NVSim pos calculated.");
        try{
        vector.add(SimCalculator.NVSim(G1, NGramTests.neu.TotalGraph));
        System.out.println("NVSim neu calculated.");
        }catch(Exception ex){vector.add((float)0.0);}
        vector.add(SimCalculator.NVSim(G1, NGramTests.neg.TotalGraph));
        System.out.println("NVSim neg calculated.");
        return vector;
    }
    
    public static ArrayList<Float> calcTriVectorFast(ArrayList<TriEdge> G1){
        ArrayList<Float> vector=new ArrayList<Float>();
        vector.add(SimCalculator.TriCSim(G1, NGramTripleGraphTests.pos.TotalGraph));
        try{
        vector.add(SimCalculator.TriCSim(G1, NGramTripleGraphTests.neu.TotalGraph));
        }catch(Exception ex){vector.add((float)0.0);}
        vector.add(SimCalculator.TriCSim(G1, NGramTripleGraphTests.neg.TotalGraph));
        return vector;
    }
    
    public static ArrayList<Float> calcTriVector(ArrayList<TriEdge> G1){
        ArrayList<Float> vector=new ArrayList<Float>();
        vector.add(SimCalculator.TriCSim(G1, NGramTripleGraphTests.pos.TotalGraph));
        try{
        vector.add(SimCalculator.TriCSim(G1, NGramTripleGraphTests.neu.TotalGraph));
        }catch(Exception ex){vector.add((float)0.0);}
        vector.add(SimCalculator.TriCSim(G1, NGramTripleGraphTests.neg.TotalGraph));
        vector.add(SimCalculator.TriVSim(G1, NGramTripleGraphTests.pos.TotalGraph));
        try{
        vector.add(SimCalculator.TriVSim(G1, NGramTripleGraphTests.neu.TotalGraph));
        }catch(Exception ex){vector.add((float)0.0);}
        vector.add(SimCalculator.TriVSim(G1, NGramTripleGraphTests.neg.TotalGraph));
        vector.add(SimCalculator.TriNVSim(G1, NGramTripleGraphTests.pos.TotalGraph));
        try{
        vector.add(SimCalculator.TriNVSim(G1, NGramTripleGraphTests.neu.TotalGraph));
        }catch(Exception ex){vector.add((float)0.0);}
        vector.add(SimCalculator.TriNVSim(G1, NGramTripleGraphTests.neg.TotalGraph));
        return vector;
    }
    
    private static String dsim(float s1, String comp1, float s2, String comp2){
        if(s1==s2) return "N/A";
        else if(s1>s2) return comp1;
        else return comp2;
    }
    
    public static ArrayList<String> discretiseProblem2(ArrayList<Float> vector){
        ArrayList<String> discVector=new ArrayList<String>();
        discVector.add(dsim(vector.get(0),"pos",vector.get(1),"neu"));
        discVector.add(dsim(vector.get(1),"neu",vector.get(2),"neg"));
        discVector.add(dsim(vector.get(0),"pos",vector.get(2),"neg"));
        discVector.add(dsim(vector.get(3),"pos",vector.get(4),"neu"));
        discVector.add(dsim(vector.get(4),"neu",vector.get(5),"neg"));
        discVector.add(dsim(vector.get(3),"pos",vector.get(5),"neg"));
        discVector.add(dsim(vector.get(6),"pos",vector.get(7),"neu"));
        discVector.add(dsim(vector.get(7),"neu",vector.get(8),"neg"));
        discVector.add(dsim(vector.get(6),"pos",vector.get(8),"neg"));
        return discVector;
    }
    
    public static ArrayList<String> discretiseProblem1(ArrayList<Float> vector){
        ArrayList<String> discVector=new ArrayList<String>();
        discVector.add(dsim(vector.get(0),"pos",vector.get(2),"neg"));
        discVector.add(dsim(vector.get(3),"pos",vector.get(5),"neg"));
        discVector.add(dsim(vector.get(6),"pos",vector.get(8),"neg"));
        return discVector;
    }
    
    public static float CSim(ArrayList<Edge> G1, ArrayList<Edge> G2){
        float cs=0;
        for(int i=0;i<G1.size();i++){
            cs+=mFunc(G1.get(i),G2)/min(G1,G2);
        }
        return cs;
    }
    
    public static float TriCSim(ArrayList<TriEdge> G1, ArrayList<TriEdge> G2){
        float cs=0;
        for(int i=0;i<G1.size();i++){
            cs+=TriMFunc(G1.get(i),G2)/TriMin(G1,G2);
        }
        return cs;
    }
    
    public static float VSim(ArrayList<Edge> G1, ArrayList<Edge> G2){
        float vs=0;
        for(int i=0;i<G1.size();i++){
            vs+=min(weightGet(G1.get(i),G1),weightGet(G1.get(i),G2))/max(weightGet(G1.get(i),G1),weightGet(G1.get(i),G2));
        }
        return vs/max(G1,G2);
    }
    
    public static float TriVSim(ArrayList<TriEdge> G1, ArrayList<TriEdge> G2){
        float vs=0;
        for(int i=0;i<G1.size();i++){
            vs+=min(TriweightGet(G1.get(i),G1),TriweightGet(G1.get(i),G2))/max(TriweightGet(G1.get(i),G1),TriweightGet(G1.get(i),G2));
        }
        return vs/TriMax(G1,G2);
    }
    
    public static float NVSim(ArrayList<Edge> G1, ArrayList<Edge> G2){
        float nvs=0;
        for(int i=0;i<G1.size();i++){
            nvs+=min(weightGet(G1.get(i),G1),weightGet(G1.get(i),G2))/max(weightGet(G1.get(i),G1),weightGet(G1.get(i),G2));
        }
        nvs=nvs/max(G1,G2);
        float ss=min(G1,G2)/max(G1,G2);
        return nvs/ss;
    }
    
    public static float TriNVSim(ArrayList<TriEdge> G1, ArrayList<TriEdge> G2){
        float nvs=0;
        for(int i=0;i<G1.size();i++){
            nvs+=min(TriweightGet(G1.get(i),G1),TriweightGet(G1.get(i),G2))/max(TriweightGet(G1.get(i),G1),TriweightGet(G1.get(i),G2));
        }
        nvs=nvs/TriMax(G1,G2);
        float ss=TriMin(G1,G2)/TriMax(G1,G2);
        return nvs/ss;
    }
    
    public static float mFunc(Edge e,ArrayList<Edge> G){
        if(G.indexOf(e)<0) return 0;
        else return 1;
    }
    
    public static float TriMFunc(TriEdge e,ArrayList<TriEdge> G){
        if(G.indexOf(e)<0) return 0;
        else return 1;
    }
    
    public static float min(ArrayList<Edge> G1,ArrayList<Edge> G2){
        if(G1.size()<=G2.size()) return G1.size();
        else return G2.size();
    }
    
     public static float TriMin(ArrayList<TriEdge> G1,ArrayList<TriEdge> G2){
        if(G1.size()<=G2.size()) return G1.size();
        else return G2.size();
    }
    
    public static float min(float G1,float G2){
        if(G1<=G2) return G1;
        else return G2;
    }
    
    public static float max(ArrayList<Edge> G1,ArrayList<Edge> G2){
        if(G1.size()>=G2.size()) return G1.size();
        else return G2.size();
    }
    
    public static float TriMax(ArrayList<TriEdge> G1,ArrayList<TriEdge> G2){
        if(G1.size()>=G2.size()) return G1.size();
        else return G2.size();
    }
    
    public static float max(float G1,float G2){
        if(G1>=G2) return G1;
        else return G2;
    }
    
    public static float weightGet(Edge e,ArrayList<Edge> G){
        if(G.indexOf(e)<0) return 0;
        else return G.get(G.indexOf(e)).getWeight();
    }
    
    public static float TriweightGet(TriEdge e,ArrayList<TriEdge> G){
        if(G.indexOf(e)<0) return 0;
        else return G.get(G.indexOf(e)).getWeight();
    }
}
