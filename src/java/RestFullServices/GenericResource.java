/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RestFullServices;

import Crawlers.TwitterCrawler;
import Entities.CustomStatus;
import Entities.SimplePercentageCorrelation;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.QueryParam;
import main_src.CombinationalNGrammGraphs;
import main_src.CombinationalNGramms;
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
@Path("analyseid")
@RequestScoped
public class GenericResource {

    @Context
    private UriInfo context;
    
    
    
    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {}
    
    /*public static void main(String[] args) {
        GenericResource gr=new GenericResource();
        gr.prepareObjects();
        gr.trainObjects();
        String papa=gr.completeTest(clear("Almost the end...The fate of #WH13 will be decided @ 9pm. Written by @Wondermasons & some blackanese dude: http://t.co/lBJ4vPsNvf #ByeWH13"),"Almost the end...The fate of #WH13 will be decided @ 9pm. Written by @Wondermasons & some blackanese dude: http://t.co/lBJ4vPsNvf #ByeWH13");
        int i=1;
    }*/
    
    

    @GET
    @Produces("text/html")
    public String getXml(@QueryParam("id") String id) {
        
        FileInputStream fstream = null;
        try {
                fstream = new FileInputStream("./results/"+id+".res");
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                fstream.close();
                in.close();
                br.close();
        }catch(Exception exe){
                Random rand=new Random((new Date()).getTime());
                long clientId=Math.abs(rand.nextLong());
                if(id.trim().toLowerCase().equals("prepare")) id="0";
                MainTestingThread mtt=new MainTestingThread(id,clientId);
                mtt.start();
                return "<!DOCTYPE html><html><head><meta HTTP-EQUIV=\"refresh\" CONTENT=\"10;URL=/ConsensusSentiTest/webresources/checkstatus?clientid="+clientId+"&mode="+id+"\"/></head><body>Analysis Started and your clientID is \""+clientId+"\".<br/>Please wait...</body></html>";
        }
        //String result=GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, clientId))).getResult();
        //GlobalVarsStore.completion.remove(new SimplePercentageCorrelation((float)0.0, clientId));
        //return result; 
        return "<!DOCTYPE html><html><head><meta HTTP-EQUIV=\"refresh\" CONTENT=\"3;URL=/ConsensusSentiTest/webresources/result?clientid=0&id="+id+"\"/></head><body>Fetching results...</body></html>";
    }

    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
  
}
