/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main_src;

import tools.GlobalVarsStore;
import weka.core.Instance;

/**
 *
 * @author ViP
 */
public class FastTest {
    
    CombinationalNGramms cmb;
    CombinationalNGrammGraphs cmbg;

    public FastTest() {
        //cmb=new CombinationalNGramms();
        //cmbg=new CombinationalNGrammGraphs();
    }
    
    /*public void train(int nGrammNumber, String trainFilename, int[] margins){
         try {
            cmb.train(trainFilename,nGrammNumber,margins);
            cmbg.train();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public double[] testSingle(String text){
        double[] res1=cmb.testSingle(cmb.textToInstanceSimple(text),"average");
        double[] res2;
        if(res1[0]>res1[1] && res1[0]>res1[2]){
            res2=cmbg.testSingle(cmbg.textToInstance(text), "average");
            if(res2[1]>res2[0] && res2[1]>res2[2]){
                res1=res2;
            }
        }
        return res1;
    }*/
    
    public static double[] testWithPretrained(String text){
        Instance simpleIns=SimpleNGramTests.textToInstanceSimple(text);
        Instance graphIns=NGramTests.textToInstance(text);
        double[] res1=GlobalVarsStore.comNgramm.testSingle(simpleIns,"average");
        double[] res2=GlobalVarsStore.comb.testSingle(graphIns,"average");
        res1[0]=res1[0]*0.3+res2[0]*0.7;
        res1[1]=res1[1]*0.5+res2[1]*0.5;
        res1[2]=res1[2]*0.7+res2[2]*0.3;
        //if(res1[0]>res1[1] && res1[0]>res1[2]){
        //    Instance graphIns=NGramTests.textToInstance(text);
        //    double[] res2=GlobalVarsStore.comb.testSingle(graphIns,"average");
        //    res1=res2;
        //}
        return res1;
    }
    
    public static double[] testWithPretrained(Instance simpleIns, Instance graphIns){
        double[] res1=GlobalVarsStore.comNgramm.testSingle(simpleIns,"average");
        double[] res2=GlobalVarsStore.comb.testSingle(graphIns,"average");
        res1[0]=res1[0]*0.3+res2[0]*0.7;
        res1[1]=res1[1]*0.5+res2[1]*0.5;
        res1[2]=res1[2]*0.7+res2[2]*0.3;
        //if(res1[0]>res1[1] && res1[0]>res1[2]){
        //    double[] res2=GlobalVarsStore.comb.testSingle(graphIns,"average");
        //    res1=res2;
        //}
        return res1;
    }
}
