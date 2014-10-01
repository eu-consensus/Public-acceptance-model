//Make a thread pie baker !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
package RestFullServices;

import Entities.CustomStatus;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
import main_src.NGramTests;
import main_src.SimpleNGramTests;
import tools.GlobalVarsStore;
import tools.MainTestingThread;
import weka.core.Instance;

/**
 * REST Web Service
 *
 * @author ViP
 */
@Path("pie")
@RequestScoped
public class pieChart {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of pieChart
     */
    public pieChart() {
    }

    /**
     * Retrieves representation of an instance of Crawlers.pieChart
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml(@QueryParam("id") String id, @QueryParam("method") String algor) {
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
                if(id.indexOf("-")>=0){
                    id=id.substring(0,id.indexOf("-"));
                }
                MainTestingThread mtt=new MainTestingThread(id,clientId);
                mtt.start();
                return "<!DOCTYPE html><html><head><meta HTTP-EQUIV=\"refresh\" CONTENT=\"10;URL=/ConsensusSentiTest/webresources/checkstatus?clientid="+clientId+"&mode=pie\"/></head><body>Analysis Started and your clientID is \""+clientId+"\".<br/>Please wait...</body></html>";
        }
        return "<!DOCTYPE html><html><head><meta HTTP-EQUIV=\"refresh\" CONTENT=\"3;URL=/ConsensusSentiTest/webresources/pieResult?clientid=0&id="+id+"\"/></head><body>Fetching results...</body></html>";
    }

    /**
     * PUT method for updating or creating an instance of pieChart
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
