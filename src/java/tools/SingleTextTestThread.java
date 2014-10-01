/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.SimplePercentageCorrelation;
import java.util.ArrayList;
import main_src.Main;
import main_src.NGramTests;
import main_src.SimpleNGramTests;
import weka.core.Instance;

/**
 *
 * @author ViP
 */
public class SingleTextTestThread extends Thread{
    private String text;
    private long clientId;
    
    public SingleTextTestThread(String text, long clientId) {
        this.text=text;
        this.clientId=clientId;
        Main.margins=new int[2];
        Main.margins[0]=0;
        Main.margins[1]=0;
        Main.ngramms=4;
        Main.threshold=(float)0.001;
    }

    @Override
    public void run() {
        String result="";
        if(GlobalVarsStore.ngt==null || GlobalVarsStore.ngt2==null || GlobalVarsStore.ngt3==null || GlobalVarsStore.ngt4==null || GlobalVarsStore.ngt5==null || GlobalVarsStore.ngt6==null || GlobalVarsStore.ngt7==null || GlobalVarsStore.ngt8==null){
             result="Models not prepared!"; 
        }
        else{
            result=completeTest(clear(text));
        }
        GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).setResult(result);
        
    }
    
    public String clearAndRun(){
        String cleartext=clear(text);
        String result="";
        ArrayList<double[]> results=new ArrayList<double[]>();
        Instance ins=NGramTests.textToInstance(cleartext);
        Instance insSimple=SimpleNGramTests.textToInstanceSimple(cleartext);
        double[] sres=GlobalVarsStore.sng.testSingle(insSimple);
        results.add(sres);
        double[] sres2=GlobalVarsStore.sng2.testSingle(insSimple);
        results.add(sres2);
        double[] sres3=GlobalVarsStore.sng3.testSingle(insSimple);
        results.add(sres3);
        double[] sres4=GlobalVarsStore.sng4.testSingle(insSimple);
        results.add(sres4);
        double[] sres5=GlobalVarsStore.sng5.testSingle(insSimple);
        results.add(sres5);
        double[] sres6=GlobalVarsStore.sng6.testSingle(insSimple);
        results.add(sres6);
        double[] sres7=GlobalVarsStore.sng7.testSingle(insSimple);
        results.add(sres7);
        double[] sres8=GlobalVarsStore.sng8.testSingle(insSimple);
        results.add(sres8);
        double[] scres=GlobalVarsStore.comNgramm.testSingle(insSimple,"average");
        results.add(scres);
        double[] scres2=GlobalVarsStore.comNgramm.testSingle(insSimple,"majority");
        results.add(scres2);
        double[] scres3=GlobalVarsStore.comNgramm.testSingle(insSimple,"euclidean","average");
        results.add(scres3);
        double[] scres4=GlobalVarsStore.comNgramm.testSingle(insSimple,"manhattan","average");
        results.add(scres4);
        double[] scres5=GlobalVarsStore.comNgramm.testSingle(insSimple,"cosine","average");
        results.add(scres5);
        double[] scres6=GlobalVarsStore.comNgramm.testSingle(insSimple,"chebychev","average");
        results.add(scres6);
        double[] scres7=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_cos","average");
        results.add(scres7);
        double[] scres8=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_sin","average");
        results.add(scres8);
        double[] scres9=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_tan","average");
        results.add(scres9);
        double[] res=GlobalVarsStore.ngt.testSingle(ins);
        results.add(res);
        double[] res2=GlobalVarsStore.ngt2.testSingle(ins);
        results.add(res2);
        double[] res3=GlobalVarsStore.ngt3.testSingle(ins);
        results.add(res3);
        double[] res4=GlobalVarsStore.ngt4.testSingle(ins);
        results.add(res4);
        double[] res5=GlobalVarsStore.ngt5.testSingle(ins);
        results.add(res5);
        double[] res6=GlobalVarsStore.ngt6.testSingle(ins);
        results.add(res6);
        double[] res7=GlobalVarsStore.ngt7.testSingle(ins);
        results.add(res7);
        double[] res8=GlobalVarsStore.ngt8.testSingle(ins);              
        results.add(res8);
        double[] rest=GlobalVarsStore.ntgt.testSingle(ins);
        results.add(rest);
        double[] rest2=GlobalVarsStore.ntgt2.testSingle(ins);
        results.add(rest2);
        double[] rest3=GlobalVarsStore.ntgt3.testSingle(ins);
        results.add(rest3);
        double[] rest4=GlobalVarsStore.ntgt4.testSingle(ins);
        results.add(rest4);
        double[] rest5=GlobalVarsStore.ntgt5.testSingle(ins);
        results.add(rest5);
        double[] rest6=GlobalVarsStore.ntgt6.testSingle(ins);
        results.add(rest6);
        double[] rest7=GlobalVarsStore.ntgt7.testSingle(ins);
        results.add(rest7);
        double[] rest8=GlobalVarsStore.ntgt8.testSingle(ins);
        results.add(rest8);
        double[] cres=GlobalVarsStore.comb.testSingle(ins,"average");
        results.add(cres);
        double[] cres2=GlobalVarsStore.comb.testSingle(ins,"majority");
        results.add(cres2);
        double[] cres3=GlobalVarsStore.comb.testSingle(ins,"euclidean","average");
        results.add(cres3);
        double[] cres4=GlobalVarsStore.comb.testSingle(ins,"manhattan","average");
        results.add(cres4);
        double[] cres5=GlobalVarsStore.comb.testSingle(ins,"cosine","average");
        results.add(cres5);
        double[] cres6=GlobalVarsStore.comb.testSingle(ins,"chebychev","average");
        results.add(cres6);
        double[] cres7=GlobalVarsStore.comb.testSingle(ins,"ortho_cos","average");
        results.add(cres7);
        double[] cres8=GlobalVarsStore.comb.testSingle(ins,"ortho_sin","average");
        results.add(cres8);
        double[] cres9=GlobalVarsStore.comb.testSingle(ins,"ortho_tan","average");
        results.add(cres9);
        double[] avres=new double[3];
        avres[0]=0;
        avres[1]=0;
        avres[2]=0;
        for (int i = 0; i < results.size() && i<17; i++) {
           avres[0]+=results.get(i)[0]*0.6;
           avres[1]+=results.get(i)[1]*0.6;
           avres[2]+=results.get(i)[2]*0.6;
        }
        for (int i = 17; i < results.size(); i++) {
           avres[0]+=results.get(i)[0];
           avres[1]+=results.get(i)[1];
           avres[2]+=results.get(i)[2];
        }
        avres[0]=avres[0]/results.size();
        avres[1]=avres[1]/results.size();
        avres[2]=avres[2]/results.size();
        
        if(avres[0] >= avres[1] && avres[0] >= avres[2]){
            result=String.format("%.4g%n", avres[0]);
        }else if(avres[1] >= avres[0] && avres[1] >= avres[2]){
            result="0";
        }else{
            result="-"+String.format("%.4g%n", avres[2]);
        }
        return result;
    }
    
    public String completeTest(String cleartext){
        String result="";
        ArrayList<double[]> results=new ArrayList<double[]>();
        Instance ins=NGramTests.textToInstance(cleartext);
        Instance insSimple=SimpleNGramTests.textToInstanceSimple(cleartext);
        double[] sres=GlobalVarsStore.sng.testSingle(insSimple);
        results.add(sres);
        double[] sres2=GlobalVarsStore.sng2.testSingle(insSimple);
        results.add(sres2);
        double[] sres3=GlobalVarsStore.sng3.testSingle(insSimple);
        results.add(sres3);
        double[] sres4=GlobalVarsStore.sng4.testSingle(insSimple);
        results.add(sres4);
        double[] sres5=GlobalVarsStore.sng5.testSingle(insSimple);
        results.add(sres5);
        double[] sres6=GlobalVarsStore.sng6.testSingle(insSimple);
        results.add(sres6);
        double[] sres7=GlobalVarsStore.sng7.testSingle(insSimple);
        results.add(sres7);
        double[] sres8=GlobalVarsStore.sng8.testSingle(insSimple);
        results.add(sres8);
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams SVM</algor><positiveScore>"+sres[0]+"</positiveScore><neutralScore>"+sres[1]+"</neutralScore><negativeScore>"+sres[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Bayes</algor><positiveScore>"+sres2[0]+"</positiveScore><neutralScore>"+sres2[1]+"</neutralScore><negativeScore>"+sres2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams C4.5</algor><positiveScore>"+sres3[0]+"</positiveScore><neutralScore>"+sres3[1]+"</neutralScore><negativeScore>"+sres3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Logistic</algor><positiveScore>"+sres4[0]+"</positiveScore><neutralScore>"+sres4[1]+"</neutralScore><negativeScore>"+sres4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Simple Logistic</algor><positiveScore>"+sres5[0]+"</positiveScore><neutralScore>"+sres5[1]+"</neutralScore><negativeScore>"+sres5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams MLP</algor><positiveScore>"+sres6[0]+"</positiveScore><neutralScore>"+sres6[1]+"</neutralScore><negativeScore>"+sres6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams BFtree</algor><positiveScore>"+sres7[0]+"</positiveScore><neutralScore>"+sres7[1]+"</neutralScore><negativeScore>"+sres7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Ftree</algor><positiveScore>"+sres8[0]+"</positiveScore><neutralScore>"+sres8[1]+"</neutralScore><negativeScore>"+sres8[2]+"</negativeScore></result>";
        double[] scres=GlobalVarsStore.comNgramm.testSingle(insSimple,"average");
        results.add(scres);
        double[] scres2=GlobalVarsStore.comNgramm.testSingle(insSimple,"majority");
        results.add(scres2);
        double[] scres3=GlobalVarsStore.comNgramm.testSingle(insSimple,"euclidean","average");
        results.add(scres3);
        double[] scres4=GlobalVarsStore.comNgramm.testSingle(insSimple,"manhattan","average");
        results.add(scres4);
        double[] scres5=GlobalVarsStore.comNgramm.testSingle(insSimple,"cosine","average");
        results.add(scres5);
        double[] scres6=GlobalVarsStore.comNgramm.testSingle(insSimple,"chebychev","average");
        results.add(scres6);
        double[] scres7=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_cos","average");
        results.add(scres7);
        double[] scres8=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_sin","average");
        results.add(scres8);
        double[] scres9=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_tan","average");
        results.add(scres9);
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Average</algor><positiveScore>"+scres[0]+"</positiveScore><neutralScore>"+scres[1]+"</neutralScore><negativeScore>"+scres[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Majority</algor><positiveScore>"+scres2[0]+"</positiveScore><neutralScore>"+scres2[1]+"</neutralScore><negativeScore>"+scres2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Euclidean</algor><positiveScore>"+scres3[0]+"</positiveScore><neutralScore>"+scres3[1]+"</neutralScore><negativeScore>"+scres3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Manhattan</algor><positiveScore>"+scres4[0]+"</positiveScore><neutralScore>"+scres4[1]+"</neutralScore><negativeScore>"+scres4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Cosine</algor><positiveScore>"+scres5[0]+"</positiveScore><neutralScore>"+scres5[1]+"</neutralScore><negativeScore>"+scres5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Chebychev</algor><positiveScore>"+scres6[0]+"</positiveScore><neutralScore>"+scres6[1]+"</neutralScore><negativeScore>"+scres6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_cos</algor><positiveScore>"+scres7[0]+"</positiveScore><neutralScore>"+scres7[1]+"</neutralScore><negativeScore>"+scres7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_sin</algor><positiveScore>"+scres8[0]+"</positiveScore><neutralScore>"+scres8[1]+"</neutralScore><negativeScore>"+scres8[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_tan</algor><positiveScore>"+scres9[0]+"</positiveScore><neutralScore>"+scres9[1]+"</neutralScore><negativeScore>"+scres9[2]+"</negativeScore></result>";
        double[] res=GlobalVarsStore.ngt.testSingle(ins);
        results.add(res);
        double[] res2=GlobalVarsStore.ngt2.testSingle(ins);
        results.add(res2);
        double[] res3=GlobalVarsStore.ngt3.testSingle(ins);
        results.add(res3);
        double[] res4=GlobalVarsStore.ngt4.testSingle(ins);
        results.add(res4);
        double[] res5=GlobalVarsStore.ngt5.testSingle(ins);
        results.add(res5);
        double[] res6=GlobalVarsStore.ngt6.testSingle(ins);
        results.add(res6);
        double[] res7=GlobalVarsStore.ngt7.testSingle(ins);
        results.add(res7);
        double[] res8=GlobalVarsStore.ngt8.testSingle(ins);              
        results.add(res8);
        result+="<result><phrase>"+cleartext+"</phrase><algor>SVM</algor><positiveScore>"+res[0]+"</positiveScore><neutralScore>"+res[1]+"</neutralScore><negativeScore>"+res[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Bayes</algor><positiveScore>"+res2[0]+"</positiveScore><neutralScore>"+res2[1]+"</neutralScore><negativeScore>"+res2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>C4.5</algor><positiveScore>"+res3[0]+"</positiveScore><neutralScore>"+res3[1]+"</neutralScore><negativeScore>"+res3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Logistic</algor><positiveScore>"+res4[0]+"</positiveScore><neutralScore>"+res4[1]+"</neutralScore><negativeScore>"+res4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Simple Logistic</algor><positiveScore>"+res5[0]+"</positiveScore><neutralScore>"+res5[1]+"</neutralScore><negativeScore>"+res5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>MLP</algor><positiveScore>"+res6[0]+"</positiveScore><neutralScore>"+res6[1]+"</neutralScore><negativeScore>"+res6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>BFtree</algor><positiveScore>"+res7[0]+"</positiveScore><neutralScore>"+res7[1]+"</neutralScore><negativeScore>"+res7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Ftree</algor><positiveScore>"+res8[0]+"</positiveScore><neutralScore>"+res8[1]+"</neutralScore><negativeScore>"+res8[2]+"</negativeScore></result>";
        double[] rest=GlobalVarsStore.ntgt.testSingle(ins);
        results.add(rest);
        double[] rest2=GlobalVarsStore.ntgt2.testSingle(ins);
        results.add(rest2);
        double[] rest3=GlobalVarsStore.ntgt3.testSingle(ins);
        results.add(rest3);
        double[] rest4=GlobalVarsStore.ntgt4.testSingle(ins);
        results.add(rest4);
        double[] rest5=GlobalVarsStore.ntgt5.testSingle(ins);
        results.add(rest5);
        double[] rest6=GlobalVarsStore.ntgt6.testSingle(ins);
        results.add(rest6);
        double[] rest7=GlobalVarsStore.ntgt7.testSingle(ins);
        results.add(rest7);
        double[] rest8=GlobalVarsStore.ntgt8.testSingle(ins);
        results.add(rest8);
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph SVM</algor><positiveScore>"+rest[0]+"</positiveScore><neutralScore>"+rest[1]+"</neutralScore><negativeScore>"+rest[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Bayes</algor><positiveScore>"+rest2[0]+"</positiveScore><neutralScore>"+rest2[1]+"</neutralScore><negativeScore>"+rest2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph C4.5</algor><positiveScore>"+rest3[0]+"</positiveScore><neutralScore>"+rest3[1]+"</neutralScore><negativeScore>"+rest3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Logistic</algor><positiveScore>"+rest4[0]+"</positiveScore><neutralScore>"+rest4[1]+"</neutralScore><negativeScore>"+rest4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Simple Logistic</algor><positiveScore>"+rest5[0]+"</positiveScore><neutralScore>"+rest5[1]+"</neutralScore><negativeScore>"+rest5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph MLP</algor><positiveScore>"+rest6[0]+"</positiveScore><neutralScore>"+rest6[1]+"</neutralScore><negativeScore>"+rest6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph BFtree</algor><positiveScore>"+rest7[0]+"</positiveScore><neutralScore>"+rest7[1]+"</neutralScore><negativeScore>"+rest7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Ftree</algor><positiveScore>"+rest8[0]+"</positiveScore><neutralScore>"+rest8[1]+"</neutralScore><negativeScore>"+rest8[2]+"</negativeScore></result>";
        double[] cres=GlobalVarsStore.comb.testSingle(ins,"average");
        results.add(cres);
        double[] cres2=GlobalVarsStore.comb.testSingle(ins,"majority");
        results.add(cres2);
        double[] cres3=GlobalVarsStore.comb.testSingle(ins,"euclidean","average");
        results.add(cres3);
        double[] cres4=GlobalVarsStore.comb.testSingle(ins,"manhattan","average");
        results.add(cres4);
        double[] cres5=GlobalVarsStore.comb.testSingle(ins,"cosine","average");
        results.add(cres5);
        double[] cres6=GlobalVarsStore.comb.testSingle(ins,"chebychev","average");
        results.add(cres6);
        double[] cres7=GlobalVarsStore.comb.testSingle(ins,"ortho_cos","average");
        results.add(cres7);
        double[] cres8=GlobalVarsStore.comb.testSingle(ins,"ortho_sin","average");
        results.add(cres8);
        double[] cres9=GlobalVarsStore.comb.testSingle(ins,"ortho_tan","average");
        results.add(cres9);
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Average</algor><positiveScore>"+cres[0]+"</positiveScore><neutralScore>"+cres[1]+"</neutralScore><negativeScore>"+cres[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Majority</algor><positiveScore>"+cres2[0]+"</positiveScore><neutralScore>"+cres2[1]+"</neutralScore><negativeScore>"+cres2[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Euclidean</algor><positiveScore>"+cres3[0]+"</positiveScore><neutralScore>"+cres3[1]+"</neutralScore><negativeScore>"+cres3[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Manhattan</algor><positiveScore>"+cres4[0]+"</positiveScore><neutralScore>"+cres4[1]+"</neutralScore><negativeScore>"+cres4[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Cosine</algor><positiveScore>"+cres5[0]+"</positiveScore><neutralScore>"+cres5[1]+"</neutralScore><negativeScore>"+cres5[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Chebychev</algor><positiveScore>"+cres6[0]+"</positiveScore><neutralScore>"+cres6[1]+"</neutralScore><negativeScore>"+cres6[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_cos</algor><positiveScore>"+cres7[0]+"</positiveScore><neutralScore>"+cres7[1]+"</neutralScore><negativeScore>"+cres7[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_sin</algor><positiveScore>"+cres8[0]+"</positiveScore><neutralScore>"+cres8[1]+"</neutralScore><negativeScore>"+cres8[2]+"</negativeScore></result>";
        result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_tan</algor><positiveScore>"+cres9[0]+"</positiveScore><neutralScore>"+cres9[1]+"</neutralScore><negativeScore>"+cres9[2]+"</negativeScore></result>";
        //double[] ftres=FastTest.testWithPretrained(insSimple, ins);
        //result+="<result><phrase>"+cleartext+"</phrase><algor>Fast test</algor><positiveScore>"+ftres[0]+"</positiveScore><neutralScore>"+ftres[1]+"</neutralScore><negativeScore>"+ftres[2]+"</negativeScore></result>";
        double[] avres=new double[3];
        avres[0]=0;
        avres[1]=0;
        avres[2]=0;
        for (int i = 0; i < results.size() && i<17; i++) {
           avres[0]+=results.get(i)[0]*0.6;
           avres[1]+=results.get(i)[1]*0.6;
           avres[2]+=results.get(i)[2]*0.6;
        }
        for (int i = 17; i < results.size(); i++) {
           avres[0]+=results.get(i)[0];
           avres[1]+=results.get(i)[1];
           avres[2]+=results.get(i)[2];
        }
        result+="<result><phrase>"+cleartext+"</phrase><algor>The Sum of all results</algor><positiveScore>"+avres[0]+"</positiveScore><neutralScore>"+avres[1]+"</neutralScore><negativeScore>"+avres[2]+"</negativeScore></result>";
        avres[0]=avres[0]/results.size();
        avres[1]=avres[1]/results.size();
        avres[2]=avres[2]/results.size();
        result+="<result><phrase>"+cleartext+"</phrase><algor>The Average of all results</algor><positiveScore>"+avres[0]+"</positiveScore><neutralScore>"+avres[1]+"</neutralScore><negativeScore>"+avres[2]+"</negativeScore></result>";
        return result;
    }
    
    private String clear(String dirty){
        String clear="";
        if(dirty.indexOf("http://")>=0){
            if(dirty.indexOf(" ", dirty.indexOf("http://"))>=0){      
                clear=dirty.toLowerCase().replaceAll("http://[\\S|\\p{Punct}]*", "URL");
            }else{
                clear=dirty.toLowerCase().replaceAll("http://.*", "URL");
            }
        }else{
        clear=dirty.toLowerCase();
        }
        if(clear.indexOf("rt ")>=0){
            clear=clear.replace("rt ", "");
        }
        if(clear.indexOf("#")>=0){
            clear=clear.replaceAll("#","");
        }
        if(clear.indexOf("@")>=0){
            clear=clear.replaceAll("@\\w+", "REF");
            if(clear.indexOf("REF:")>=0){
                clear=clear.replaceAll("REF:", "");
            }
        }
        if(clear.indexOf("\n")>=0){
            clear=clear.replaceAll("\\n", " ");
        }
        if(clear.indexOf("& ")>=0){
            clear=clear.replaceAll("& ", "and ");
        }
        if(clear.indexOf("&")>=0){
            clear=clear.replaceAll("&", "");
        }
        return clear.trim();
    }
}
