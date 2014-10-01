/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RestFullServices;

import Entities.SimplePercentageCorrelation;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
import tools.GlobalVarsStore;

/**
 * REST Web Service
 *
 * @author ViP
 */
@Path("result")
@RequestScoped
public class Result {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Result
     */
    public Result() {
    }

    /**
     * Retrieves representation of an instance of Crawlers.Result
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml(@QueryParam("id") String id, @QueryParam("clientid") String clientid) {
        String result="<results><result>Results not Found.</result></results>";
        try{
            result=GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, Long.parseLong(clientid)))).getResult();
            GlobalVarsStore.completion.remove(new SimplePercentageCorrelation((float)0.0, Long.parseLong(clientid)));
        }catch(Exception ex){
            FileInputStream fstream = null;
            try {
                fstream = new FileInputStream("./results/"+id+".res");
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                result="";
                while ((strLine = br.readLine()) != null) {
                    result+=strLine;
                }
                fstream.close();
                in.close();
                br.close();
            }catch(Exception exe){}
        }
        return result;        
    }

    /**
     * PUT method for updating or creating an instance of Result
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
