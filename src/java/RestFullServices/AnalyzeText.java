/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RestFullServices;

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
@Path("analyzetext")
@RequestScoped
public class AnalyzeText {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AnalyzeText
     */
    public AnalyzeText() {
    }

    /**
     * Retrieves representation of an instance of Crawlers.AnalyzeText
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml(@QueryParam("text") String text) {
        System.out.println("\nText= "+text+"\n");
        text=org.apache.commons.lang3.StringEscapeUtils.unescapeHtml3(text);
        System.out.println("\nText= "+text+"\n");
        Random rand=new Random((new Date()).getTime());
                long clientId=Math.abs(rand.nextLong());
                SingleTextTestThread mtt=new SingleTextTestThread(text,clientId);
                return mtt.clearAndRun();
    }

    /**
     * PUT method for updating or creating an instance of AnalyzeText
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
