/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RestFullServices;

import Crawlers.TwitterCrawler;
import Entities.CustomStatus;
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
import tools.SingleTextTestThread;

/**
 * REST Web Service
 *
 * @author ViP
 */
@Path("analyzeid")
@RequestScoped
public class AnalyzeID {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AnalyzeID
     */
    public AnalyzeID() {
    }

    /**
     * Retrieves representation of an instance of RestFullServices.AnalyzeID
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getXml(@QueryParam("id") String id) {
        CustomStatus cs;
        try{
        TwitterCrawler tc=new TwitterCrawler();
        cs=tc.getById(id);
        Random rand=new Random((new Date()).getTime());
        long clientId=Math.abs(rand.nextLong());
        SingleTextTestThread mtt=new SingleTextTestThread(cs.getText(),clientId);
        return "Sentiment Weight: "+mtt.clearAndRun()+"<div class=\"tweetelement\">"+cs.getText()+"</div>";
        }catch(Exception ex){
            return "Error";
        }   
    }

    /**
     * PUT method for updating or creating an instance of AnalyzeID
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
