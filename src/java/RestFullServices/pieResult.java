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
import tools.GlobalVarsStore;

/**
 * REST Web Service
 *
 * @author ViP
 */
@Path("pieResult")
@RequestScoped
public class pieResult {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of pieResult
     */
    public pieResult() {
    }

    /**
     * Retrieves representation of an instance of Crawlers.pieResult
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml(@QueryParam("id") String id, @QueryParam("clientid") String clientid) {
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
        if(!result.equals("<results><result>Results not Found.</result></results>")){
            result=bakePie(result);
        }
        return result;
    }

    /**
     * PUT method for updating or creating an instance of pieResult
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }

    private String bakePie(String result) {
        String pie="<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"utf-8\" />"+
                   "<link rel=\"stylesheet\" type=\"text/css\" href=\"http://www.shieldui.com/shared/components/latest/css/shieldui-all.min.css\" />"+
                   "<link rel=\"stylesheet\" type=\"text/css\" href=\"http://www.shieldui.com/shared/components/latest/css/light-mint/all.min.css\" />"+
                   "<script type=\"text/javascript\" src=\"http://www.shieldui.com/shared/components/latest/js/jquery-1.10.2.min.js\"></script>"+
                   "<script type=\"text/javascript\" src=\"http://www.shieldui.com/shared/components/latest/js/shieldui-all.min.js\"></script>"+
                   "</head><body class=\"theme-light\"><div id=\"chart\" style=\"width: 1024px;height:950px;margin:0 auto;\"></div><script type=\"text/javascript\">"+
                   "$(function () {$(\"#chart\").shieldChart({theme: \"bootstrap\", exportOptions: {image: false, print: false}, seriesSettings: {"+
                   "polarbar: { stackMode: \"normal\"}}, axisX: {categoricalValues: [\"Positive\", \"Neutral\", \"Negative\"]}, primaryHeader: {"+
                   "text: \"Category Distributions\"}, chartLegend: {align: \"center\", verticalAlign: \"bottom\"}, dataSeries: [";
        String algor="";
        String positive="";
        String neutral="";
        String negative="";
        String temp="";
        while(result.contains("<result>")){
            temp=result.substring(result.indexOf("<result>")+"<result>".length(), result.indexOf("</result>"));
            algor=temp.substring(temp.indexOf("<algor>")+"<algor>".length(),temp.indexOf("</algor>"));
            positive=temp.substring(temp.indexOf("<positiveScore>")+"<positiveScore>".length(),temp.indexOf("</positiveScore>"));
            neutral=temp.substring(temp.indexOf("<neutralScore>")+"<neutralScore>".length(),temp.indexOf("</neutralScore>"));
            negative=temp.substring(temp.indexOf("<negativeScore>")+"<negativeScore>".length(),temp.indexOf("</negativeScore>"));
            if(!algor.toLowerCase().trim().equals("the sum of all results") && !algor.toLowerCase().trim().equals("the average of all results"))
               pie+="{seriesType: \"polarbar\",collectionAlias: \""+algor+"\",data: ["+positive+","+neutral+","+negative+"]},";
            result=result.substring(result.indexOf("</result>")+"</result>".length());
        }
        pie=pie.substring(0,pie.length()-1);
        pie+="]}); texts=document.getElementsByTagName(\"text\"); len=texts.length; for(var i=0;i<len;i=i+1){"+
             "if(texts[i].getAttribute(\"zIndex\")=='8'){texts[i].style.display = 'none';}}});</script></body></html>";
        return pie;
    }
}
