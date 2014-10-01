/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RestFullServices;

import Crawlers.TwitterCrawler;
import Entities.CustomStatus;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.QueryParam;
import main_src.CombinationalNGrammGraphs;
import main_src.CombinationalNGramms;
import main_src.FastTest;
import main_src.Main;
import main_src.NGramTests;
import main_src.NGramTripleGraphTests;
import main_src.SimpleNGramTests;
import tools.GlobalVarsStore;
import tools.MainTestingThread;
import weka.core.Instance;

/**
 * REST Web Service
 *
 * @author ViP
 */
@Path("test")
@RequestScoped
public class TestIDs {

    @Context
    private UriInfo context;

    public TestIDs() {
        Main.margins=new int[2];
        Main.margins[0]=0;
        Main.margins[1]=0;
        Main.ngramms=4;
        Main.threshold=(float)0.001;
    }

    /**
     * Retrieves representation of an instance of Crawlers.TestIDs
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml(@QueryParam("id") String id, @QueryParam("method") String algor) {
        String result="";
                if(GlobalVarsStore.ngt==null || GlobalVarsStore.ngt2==null || GlobalVarsStore.ngt3==null || GlobalVarsStore.ngt4==null || GlobalVarsStore.ngt5==null || GlobalVarsStore.ngt6==null || GlobalVarsStore.ngt7==null || GlobalVarsStore.ngt8==null){
                    result="<results>";
                    result+="Models not prepared!"; 
                    result+="</results>";
                }else{
                    if(algor==null){
                        result+="<commandlist>";
                        result+="<command><method_literal>simplesvm</method_literal><algorithm>Support Vector Machine</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simplebayes</method_literal><algorithm>Bayesian Networks</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simplec45</method_literal><algorithm>C4.5</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simplelogistic</method_literal><algorithm>Logistic Regression</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simplesimplelogistic</method_literal><algorithm>Simple Logistic Regression</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simplemlp</method_literal><algorithm>Multilayer Perceptrons</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simplebftree</method_literal><algorithm>Best-First Trees</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simpleft</method_literal><algorithm>Functional Trees</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>graphsvm</method_literal><algorithm>Support Vector Machine</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphbayes</method_literal><algorithm>Bayesian Networks</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphc45</method_literal><algorithm>C4.5</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphlogistic</method_literal><algorithm>Logistic Regression</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphsimplelogistic</method_literal><algorithm>Simple Logistic Regression</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphmlp</method_literal><algorithm>Multilayer Perceptrons</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphbftree</method_literal><algorithm>Best-First Trees</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphft</method_literal><algorithm>Functional Trees</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>triplegraphsvm</method_literal><algorithm>Support Vector Machine</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>triplegraphbayes</method_literal><algorithm>Bayesian Networks</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>triplegraphc45</method_literal><algorithm>C4.5</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>triplegraphlogistic</method_literal><algorithm>Logistic Regression</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>triplegraphsimplelogistic</method_literal><algorithm>Simple Logistic Regression</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>triplegraphmlp</method_literal><algorithm>Multilayer Perceptrons</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>triplegraphbftree</method_literal><algorithm>Best-First Trees</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>triplegraphft</method_literal><algorithm>Functional Trees</algorithm><nlp_method>Triple N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>simpleaverage</method_literal><algorithm>Combinational with Average Rule</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simplemajority</method_literal><algorithm>Combinational with Majority Rule</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simpleeuclidean</method_literal><algorithm>Combinational with Euclidean Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simplemanhattan</method_literal><algorithm>Combinational with Manhattan Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simplecosine</method_literal><algorithm>Combinational with Cosine Dissimilarity</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simplechebychev</method_literal><algorithm>Combinational with Chebychev Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simpleorthocos</method_literal><algorithm>Combinational with Cos-based Orthodromic Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simpleorthosin</method_literal><algorithm>Combinational with Sin-based Orthodromic Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>simpleorthotan</method_literal><algorithm>Combinational with Tan-based Orthodromic Distances</algorithm><nlp_method>N-Grams</nlp_method></command>";
                        result+="<command><method_literal>grapheaverage</method_literal><algorithm>Combinational with Average Rule</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphemajority</method_literal><algorithm>Combinational with Majority Rule</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>grapheuclidean</method_literal><algorithm>Combinational with Euclidean Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphmanhattan</method_literal><algorithm>Combinational with Manhattan Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphcosine</method_literal><algorithm>Combinational with Cosine Dissimilarity</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphchebychev</method_literal><algorithm>Combinational with Chebychev Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphorthocos</method_literal><algorithm>Combinational with Cos-based Orthodromic Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphorthosin</method_literal><algorithm>Combinational with Sin-based Orthodromic Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>graphorthotan</method_literal><algorithm>Combinational with Tan-based Orthodromic Distances</algorithm><nlp_method>N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>fasttest</method_literal><algorithm>A hybrid combination between N-Grams and Graphs</algorithm><nlp_method>N-Grams and N-Gram Graphs</nlp_method></command>";
                        result+="<command><method_literal>averagetest</method_literal><algorithm>An Averaged Sum of all N-Grams and Graphs</algorithm><nlp_method>N-Grams and N-Gram Graphs</nlp_method></command>";
                        result+="</commandlist>";
                    }else{
                        TwitterCrawler tc=new TwitterCrawler();
                        ArrayList<CustomStatus> statuses=new ArrayList<CustomStatus>();
                        String[] ids=id.split("-");
                        for (int i = 0; i < ids.length; i++) {
                            CustomStatus cs=tc.getById(ids[i]);
                            if(cs!=null) statuses.add(cs);
                        }
                        result="<results>";
                        if(statuses.size()>0){
                        System.out.println("Starting Tests...");
                            for (int i = 0; i < statuses.size(); i++) {
                                result+=completeTest(statuses.get(i).getClearText(),statuses.get(i).getText(),algor);
                            }
                        //result+=completeTest("This game is wonderful, gratz to all guys :D");
                        System.out.println("Tests Completed.");
                        }else{
                           result+="Tweet not found."; 
                        }
                        result+="</results>";
                    }
                }
        return result;
    }

    /**
     * PUT method for updating or creating an instance of TestIDs
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
    
    private String completeTest(String cleartext,String text,String algor){
        String result="";
            Instance insSimple=new Instance(0);
            Instance ins=new Instance(0);
            if(algor.toLowerCase().contains("simple")){
                insSimple=SimpleNGramTests.textToInstanceSimple(cleartext);
            }else if(!algor.toLowerCase().equals("fasttest")){
                ins=NGramTests.textToInstance(cleartext);
            }
            if(algor.toLowerCase().trim().equals("simplesvm")){
                double[] sres=GlobalVarsStore.sng.testSingle(insSimple);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams SVM</algor><positiveScore>"+sres[0]+"</positiveScore><neutralScore>"+sres[1]+"</neutralScore><negativeScore>"+sres[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simplebayes")){
                double[] sres2=GlobalVarsStore.sng2.testSingle(insSimple);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Bayes</algor><positiveScore>"+sres2[0]+"</positiveScore><neutralScore>"+sres2[1]+"</neutralScore><negativeScore>"+sres2[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simplec45")){
                double[] sres3=GlobalVarsStore.sng3.testSingle(insSimple);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams C4.5</algor><positiveScore>"+sres3[0]+"</positiveScore><neutralScore>"+sres3[1]+"</neutralScore><negativeScore>"+sres3[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simplelogistic")){
                double[] sres4=GlobalVarsStore.sng4.testSingle(insSimple);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Logistic</algor><positiveScore>"+sres4[0]+"</positiveScore><neutralScore>"+sres4[1]+"</neutralScore><negativeScore>"+sres4[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simplesimplelogistic")){
                double[] sres5=GlobalVarsStore.sng5.testSingle(insSimple);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Simple Logistic</algor><positiveScore>"+sres5[0]+"</positiveScore><neutralScore>"+sres5[1]+"</neutralScore><negativeScore>"+sres5[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simplemlp")){
                double[] sres6=GlobalVarsStore.sng6.testSingle(insSimple);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams MLP</algor><positiveScore>"+sres6[0]+"</positiveScore><neutralScore>"+sres6[1]+"</neutralScore><negativeScore>"+sres6[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simplebftree")){
                double[] sres7=GlobalVarsStore.sng7.testSingle(insSimple);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams BFtree</algor><positiveScore>"+sres7[0]+"</positiveScore><neutralScore>"+sres7[1]+"</neutralScore><negativeScore>"+sres7[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simpleft")){
                double[] sres8=GlobalVarsStore.sng8.testSingle(insSimple);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Simple NGrams Ftree</algor><positiveScore>"+sres8[0]+"</positiveScore><neutralScore>"+sres8[1]+"</neutralScore><negativeScore>"+sres8[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphsvm")){
                double[] res=GlobalVarsStore.ngt.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>SVM</algor><positiveScore>"+res[0]+"</positiveScore><neutralScore>"+res[1]+"</neutralScore><negativeScore>"+res[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphbayes")){
                double[] res2=GlobalVarsStore.ngt2.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Bayes</algor><positiveScore>"+res2[0]+"</positiveScore><neutralScore>"+res2[1]+"</neutralScore><negativeScore>"+res2[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphc45")){
                double[] res3=GlobalVarsStore.ngt3.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>C4.5</algor><positiveScore>"+res3[0]+"</positiveScore><neutralScore>"+res3[1]+"</neutralScore><negativeScore>"+res3[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphlogistic")){
                double[] res4=GlobalVarsStore.ngt4.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Logistic</algor><positiveScore>"+res4[0]+"</positiveScore><neutralScore>"+res4[1]+"</neutralScore><negativeScore>"+res4[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphsimplelogistic")){
                double[] res5=GlobalVarsStore.ngt5.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Simple Logistic</algor><positiveScore>"+res5[0]+"</positiveScore><neutralScore>"+res5[1]+"</neutralScore><negativeScore>"+res5[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphmlp")){
                double[] res6=GlobalVarsStore.ngt6.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>MLP</algor><positiveScore>"+res6[0]+"</positiveScore><neutralScore>"+res6[1]+"</neutralScore><negativeScore>"+res6[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphbftree")){
                double[] res7=GlobalVarsStore.ngt7.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>BFtree</algor><positiveScore>"+res7[0]+"</positiveScore><neutralScore>"+res7[1]+"</neutralScore><negativeScore>"+res7[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphft")){
                double[] res8=GlobalVarsStore.ngt8.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Ftree</algor><positiveScore>"+res8[0]+"</positiveScore><neutralScore>"+res8[1]+"</neutralScore><negativeScore>"+res8[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("triplegraphsvm")){
                double[] rest=GlobalVarsStore.ntgt.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph SVM</algor><positiveScore>"+rest[0]+"</positiveScore><neutralScore>"+rest[1]+"</neutralScore><negativeScore>"+rest[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("triplegraphbayes")){
                double[] rest2=GlobalVarsStore.ntgt2.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Bayes</algor><positiveScore>"+rest2[0]+"</positiveScore><neutralScore>"+rest2[1]+"</neutralScore><negativeScore>"+rest2[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("triplegraphc45")){
                double[] rest3=GlobalVarsStore.ntgt3.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph C4.5</algor><positiveScore>"+rest3[0]+"</positiveScore><neutralScore>"+rest3[1]+"</neutralScore><negativeScore>"+rest3[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("triplegraphlogistic")){
                double[] rest4=GlobalVarsStore.ntgt4.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Logistic</algor><positiveScore>"+rest4[0]+"</positiveScore><neutralScore>"+rest4[1]+"</neutralScore><negativeScore>"+rest4[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("triplegraphsimplelogistic")){
                double[] rest5=GlobalVarsStore.ntgt5.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Simple Logistic</algor><positiveScore>"+rest5[0]+"</positiveScore><neutralScore>"+rest5[1]+"</neutralScore><negativeScore>"+rest5[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("triplegraphmlp")){
                double[] rest6=GlobalVarsStore.ntgt6.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph MLP</algor><positiveScore>"+rest6[0]+"</positiveScore><neutralScore>"+rest6[1]+"</neutralScore><negativeScore>"+rest6[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("triplegraphbftree")){
                double[] rest7=GlobalVarsStore.ntgt7.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph BFtree</algor><positiveScore>"+rest7[0]+"</positiveScore><neutralScore>"+rest7[1]+"</neutralScore><negativeScore>"+rest7[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("triplegraphft")){
                double[] rest8=GlobalVarsStore.ntgt8.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Triple Graph Ftree</algor><positiveScore>"+rest8[0]+"</positiveScore><neutralScore>"+rest8[1]+"</neutralScore><negativeScore>"+rest8[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simpleeuclidean")){
                double[] scres=GlobalVarsStore.comNgramm.testSingle(insSimple,"euclidean","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Euclidean</algor><positiveScore>"+scres[0]+"</positiveScore><neutralScore>"+scres[1]+"</neutralScore><negativeScore>"+scres[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simplemanhattan")){
                double[] scres2=GlobalVarsStore.comNgramm.testSingle(insSimple,"manhattan","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Manhattan</algor><positiveScore>"+scres2[0]+"</positiveScore><neutralScore>"+scres2[1]+"</neutralScore><negativeScore>"+scres2[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simplecosine")){
                double[] scres3=GlobalVarsStore.comNgramm.testSingle(insSimple,"cosine","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Cosine</algor><positiveScore>"+scres3[0]+"</positiveScore><neutralScore>"+scres3[1]+"</neutralScore><negativeScore>"+scres3[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simplechebychev")){
                double[] scres4=GlobalVarsStore.comNgramm.testSingle(insSimple,"chebychev","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Chebychev</algor><positiveScore>"+scres4[0]+"</positiveScore><neutralScore>"+scres4[1]+"</neutralScore><negativeScore>"+scres4[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simpleorthocos")){
                double[] scres5=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_cos","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_cos</algor><positiveScore>"+scres5[0]+"</positiveScore><neutralScore>"+scres5[1]+"</neutralScore><negativeScore>"+scres5[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simpleorthosin")){
                double[] scres6=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_sin","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_sin</algor><positiveScore>"+scres6[0]+"</positiveScore><neutralScore>"+scres6[1]+"</neutralScore><negativeScore>"+scres6[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simpleorthotan")){
                double[] scres7=GlobalVarsStore.comNgramm.testSingle(insSimple,"ortho_tan","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Ortho_tan</algor><positiveScore>"+scres7[0]+"</positiveScore><neutralScore>"+scres7[1]+"</neutralScore><negativeScore>"+scres7[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simpleaverage")){
                double[] scres8=GlobalVarsStore.comNgramm.testSingle(insSimple,"average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Average</algor><positiveScore>"+scres8[0]+"</positiveScore><neutralScore>"+scres8[1]+"</neutralScore><negativeScore>"+scres8[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("simplemajority")){
                double[] scres9=GlobalVarsStore.comNgramm.testSingle(insSimple,"majority");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Average</algor><positiveScore>"+scres9[0]+"</positiveScore><neutralScore>"+scres9[1]+"</neutralScore><negativeScore>"+scres9[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("grapheuclidean")){
                double[] cres=GlobalVarsStore.comb.testSingle(ins,"euclidean","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Euclidean</algor><positiveScore>"+cres[0]+"</positiveScore><neutralScore>"+cres[1]+"</neutralScore><negativeScore>"+cres[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphmanhattan")){
                double[] cres2=GlobalVarsStore.comb.testSingle(ins,"manhattan","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Manhattan</algor><positiveScore>"+cres2[0]+"</positiveScore><neutralScore>"+cres2[1]+"</neutralScore><negativeScore>"+cres2[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphcosine")){
                double[] cres3=GlobalVarsStore.comb.testSingle(ins,"cosine","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Cosine</algor><positiveScore>"+cres3[0]+"</positiveScore><neutralScore>"+cres3[1]+"</neutralScore><negativeScore>"+cres3[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphchebychev")){
                double[] cres4=GlobalVarsStore.comb.testSingle(ins,"chebychev","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Chebychev</algor><positiveScore>"+cres4[0]+"</positiveScore><neutralScore>"+cres4[1]+"</neutralScore><negativeScore>"+cres4[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphorthocos")){
                double[] cres5=GlobalVarsStore.comb.testSingle(ins,"ortho_cos","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_cos</algor><positiveScore>"+cres5[0]+"</positiveScore><neutralScore>"+cres5[1]+"</neutralScore><negativeScore>"+cres5[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphorthosin")){
                double[] cres6=GlobalVarsStore.comb.testSingle(ins,"ortho_sin","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_sin</algor><positiveScore>"+cres6[0]+"</positiveScore><neutralScore>"+cres6[1]+"</neutralScore><negativeScore>"+cres6[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphorthotan")){
                double[] cres7=GlobalVarsStore.comb.testSingle(ins,"ortho_tan","average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Graph Ortho_tan</algor><positiveScore>"+cres7[0]+"</positiveScore><neutralScore>"+cres7[1]+"</neutralScore><negativeScore>"+cres7[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphaverage")){
                double[] cres8=GlobalVarsStore.comb.testSingle(ins,"average");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Average</algor><positiveScore>"+cres8[0]+"</positiveScore><neutralScore>"+cres8[1]+"</neutralScore><negativeScore>"+cres8[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("graphmajority")){
                double[] cres9=GlobalVarsStore.comb.testSingle(ins,"majority");
                result+="<result><phrase>"+cleartext+"</phrase><algor>Combinational Majority</algor><positiveScore>"+cres9[0]+"</positiveScore><neutralScore>"+cres9[1]+"</neutralScore><negativeScore>"+cres9[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("fasttest")){
                double[] ftres=FastTest.testWithPretrained(cleartext);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Fast test</algor><positiveScore>"+ftres[0]+"</positiveScore><neutralScore>"+ftres[1]+"</neutralScore><negativeScore>"+ftres[2]+"</negativeScore></result>";
            }else if(algor.toLowerCase().trim().equals("averagetest")){
                MainTestingThread ntt=new MainTestingThread(0,0);
                double[] avres=ntt.averageTest(insSimple, ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>Average test</algor><positiveScore>"+avres[0]+"</positiveScore><neutralScore>"+avres[1]+"</neutralScore><negativeScore>"+avres[2]+"</negativeScore></result>";
            }else{
                double[] res6=GlobalVarsStore.ngt6.testSingle(ins);
                result+="<result><phrase>"+cleartext+"</phrase><algor>MLP</algor><positiveScore>"+res6[0]+"</positiveScore><neutralScore>"+res6[1]+"</neutralScore><negativeScore>"+res6[2]+"</negativeScore></result>";
            }
        return result;
    }
    
}
