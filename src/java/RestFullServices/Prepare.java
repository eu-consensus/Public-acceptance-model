/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RestFullServices;

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
import tools.MainTestingThread;

/**
 * REST Web Service
 *
 * @author ViP
 */
@Path("prepare")
@RequestScoped
public class Prepare {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Prepare
     */
    public Prepare() {
    }

    /**
     * Retrieves representation of an instance of RestFullServices.Prepare
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getXml(@QueryParam("username") String username,@QueryParam("password") String password) {
                if(username.equals("Admin") && password.equals("AdminPass")){
                    Random rand=new Random((new Date()).getTime());
                    long clientId=Math.abs(rand.nextLong());
                    MainTestingThread mtt=new MainTestingThread("prepare",clientId);
                    mtt.start();
                    return "<!DOCTYPE html><html><head><meta HTTP-EQUIV=\"refresh\" CONTENT=\"10;URL=/ConsensusSentiTest/webresources/checkstatus?clientid="+clientId+"&mode=prepare\"/></head><body>Preparation Started.<br/>Please wait...</body></html>";
                }else{
                    return "<!DOCTYPE html><html><head></head><body>Wrong Admin name or password, try again.</body></html>";
                }
    }

    /**
     * PUT method for updating or creating an instance of Prepare
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
