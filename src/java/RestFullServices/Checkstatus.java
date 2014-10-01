/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RestFullServices;

import Entities.SimplePercentageCorrelation;
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
@Path("checkstatus")
@RequestScoped
public class Checkstatus {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Checkstatus
     */
    public Checkstatus() {
    }

    /**
     * Retrieves representation of an instance of Crawlers.Checkstatus
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getXml(@QueryParam("clientid") String clientid,@QueryParam("mode") String mode) {
        float completion=0;
        try{
            completion=GlobalVarsStore.completion.get(GlobalVarsStore.completion.indexOf(new SimplePercentageCorrelation((float)0.0, Long.parseLong(clientid)))).getPercentage();
            if(completion < 100){
                if(mode.toLowerCase().trim().equals("prepare")){
                    return "<!DOCTYPE html><html><head><meta HTTP-EQUIV=\"refresh\" CONTENT=\"10;URL=/ConsensusSentiTest/webresources/checkstatus?clientid="+clientid+"&mode="+mode+"\"/></head><body>Preparation completion: "+completion+"%</body></html>";
                }else if(mode.toLowerCase().trim().equals("pie")){
                    return "<!DOCTYPE html><html><head><meta HTTP-EQUIV=\"refresh\" CONTENT=\"10;URL=/ConsensusSentiTest/webresources/checkstatus?clientid="+clientid+"&mode="+mode+"\"/></head><body>Analysis completion: "+completion+"%</body></html>";
                }else{
                    return "<!DOCTYPE html><html><head><meta HTTP-EQUIV=\"refresh\" CONTENT=\"10;URL=/ConsensusSentiTest/webresources/checkstatus?clientid="+clientid+"&mode="+mode+"\"/></head><body>Analysis completion: "+completion+"%</body></html>";
                }
            }else{
                if(mode.toLowerCase().trim().equals("pie")){  
                    return "<!DOCTYPE html><html><head><meta HTTP-EQUIV=\"refresh\" CONTENT=\"2;URL=/ConsensusSentiTest/webresources/pieResult?id="+mode+"&clientid="+clientid+"\"/></head><body>Fetching chart...</body></html>";
                }else{
                    return "<!DOCTYPE html><html><head><meta HTTP-EQUIV=\"refresh\" CONTENT=\"2;URL=/ConsensusSentiTest/webresources/result?id="+mode+"&clientid="+clientid+"\"/></head><body>Fetching results...</body></html>";
                }
            }
        }catch(NumberFormatException ex){return "wrong id";}
        catch(Exception ex){return "Link outdated, try <a href=\"/ConsensusSentiTest/webresources/result?clientid="+clientid+"\">Get Results</a>.";}
   }

    /**
     * PUT method for updating or creating an instance of Checkstatus
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
