/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RestFullServices;

import Crawlers.TwitterCrawler;
import Entities.CustomStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
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
import tools.SingleTextTestThread;

/**
 * REST Web Service
 *
 * @author ViP
 */
@Path("analyzekeyword")
@RequestScoped
public class AnalyzeKeyword {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AnalyzeKeyword
     */
    public AnalyzeKeyword() {
    }

    /**
     * Retrieves representation of an instance of RestFullServices.AnalyzeKeyword
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml(@QueryParam("keyword") String keyword)  {
        String resulting="<table>";
        ArrayList<CustomStatus> cs;
        try{
        TwitterCrawler tc=new TwitterCrawler();
        cs=new ArrayList<CustomStatus>(tc.smallsearch(keyword));
        Random rand=new Random((new Date()).getTime());
        long clientId=Math.abs(rand.nextLong());
        SingleTextTestThread mtt;
        Double aggr=0.0;
        for (int i = 0; i < cs.size(); i++) {
            mtt=new SingleTextTestThread(cs.get(i).getText(),clientId);
            Double cur=Double.parseDouble(mtt.clearAndRun());
            aggr+=cur;
            resulting+="<tr class=\"tweetelement\"><td>"+cs.get(i).getText()+"</td><td>"+String.format("%.4g%n", cur)+"</td></tr>";
        }
        return "Sentiment Weight: "+String.format("%.4g%n", aggr/cs.size())+resulting;
        }catch(Exception ex){
            return "Error";
        }   
    }

    /**
     * PUT method for updating or creating an instance of AnalyzeKeyword
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
