/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import Entities.SimplePercentageCorrelation;
import java.util.ArrayList;
import main_src.CombinationalNGrammGraphs;
import main_src.CombinationalNGramms;
import main_src.FastTest;
import main_src.NGramTests;
import main_src.NGramTripleGraphTests;
import main_src.SimpleNGramTests;
import main_src.WordNetTests;

/**
 *
 * @author ViP
 */
public class GlobalVarsStore {
    
    public static SimpleNGramTests sng;
    public static SimpleNGramTests sng2;
    public static SimpleNGramTests sng3;
    public static SimpleNGramTests sng4;
    public static SimpleNGramTests sng5;
    public static SimpleNGramTests sng6;
    public static SimpleNGramTests sng7;
    public static SimpleNGramTests sng8;
    public static NGramTests ngt;
    public static NGramTests ngt2;
    public static NGramTests ngt3;
    public static NGramTests ngt4;
    public static NGramTests ngt5;
    public static NGramTests ngt6;
    public static NGramTests ngt7;
    public static NGramTests ngt8;
    public static WordNetTests wnt;
    public static CombinationalNGrammGraphs comb;
    public static CombinationalNGramms comNgramm;
    public static NGramTripleGraphTests ntgt;
    public static NGramTripleGraphTests ntgt2;
    public static NGramTripleGraphTests ntgt3;
    public static NGramTripleGraphTests ntgt4;
    public static NGramTripleGraphTests ntgt5;
    public static NGramTripleGraphTests ntgt6;
    public static NGramTripleGraphTests ntgt7;
    public static NGramTripleGraphTests ntgt8;
    public static ArrayList<SimplePercentageCorrelation> completion=new ArrayList<SimplePercentageCorrelation>();
    public static FastTest ft=new FastTest();
    public static String trainDataDirectory;
    public static String objectDirectory;
    public static boolean fast;
    
}
