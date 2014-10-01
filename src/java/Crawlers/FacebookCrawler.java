/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Crawlers;

import Entities.CustomFBStatus;
import Entities.CustomStatus;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ViP
 */
public class FacebookCrawler {
    Facebook facebook;

    public FacebookCrawler() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthAppId("265251120320897")
            .setOAuthAppSecret("9c69f899edeef85be45b95ec0c7e366b")
            .setOAuthAccessToken("265251120320897|9c69f899edeef85be45b95ec0c7e366b")
            //.setOAuthAccessToken("CAACEdEose0cBALr3CQRYXIOHc5lvBZAHuaSDcsReZBbofcUJbMMzwM0faBINKTjJ8xxO8YN51pZCNSmvy4WbmqOBQD6yJmxGuteQKrgZCLcJ2XFgcIy24OCqAcZCid73trF0KHUtNJhPB8BdPP1HMi1pjX4IplztCL3ZANKZCLflkDEppkv0GMKmf7gABewhSqxAY5MzRKxHgZDZD")
            .setOAuthPermissions("read_stream,read_insights");
        facebook = new FacebookFactory(cb.build()).getInstance();
    }
   
    /*public List<CustomFBStatus> search(String keyword) {
        try{
        ResponseList<Post> results = facebook.searchPosts(keyword);
            List<CustomFBStatus> resultPosts=new ArrayList<CustomFBStatus>();
            for(int i=0;i<results.size();i++){
                Post ps=results.get(i);
                resultPosts.add(new CustomFBStatus(ps.getId(), ps.getCreatedTime(), ps.getDescription(), ps.getLikes().size(), nullToZero(ps.getSharesCount())));
            }
            return resultPosts;
        } catch (FacebookException e) {
            e.printStackTrace();
            return null;
        }
    }*/
    
    public List<CustomFBStatus> search(String keyword) {
        List<CustomFBStatus> resultPosts=new ArrayList<CustomFBStatus>();
        try{
            ResponseList<JSONObject> results = facebook.search(keyword);
            for(int i=0;i<results.size();i++){
                JSONObject ps=results.get(i);
                String text="";
                try {text+=ps.getString("name")+" ";} catch (JSONException ex) {}
                try {text+=ps.getString("story")+" ";}catch (JSONException ex) {}
                try {text+=ps.getString("message")+" ";}catch (JSONException ex) {}
                try {text+=ps.getString("description")+" ";}catch (JSONException ex) {}
                resultPosts.add(new CustomFBStatus(ps.getString("id"), ps.getString("created_time"), text, 0, 0));
                System.out.println(ps.getString("id")+"\n"+text+"\n-------------------------------------------------------------------------------\n");
            }
            return resultPosts;
        } catch (FacebookException e) {
            e.printStackTrace();
            return resultPosts;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return resultPosts;
        }
    }
    
    /*public List<CustomFBStatus> search(String keyword) {
        List<CustomFBStatus> resultPosts=new ArrayList<CustomFBStatus>();
        try{
            //System.out.println(facebook.getMe().getId());
            String query="SELECT created_time, description, message, like_info, post_id, share_count, type, comment_info FROM stream WHERE filter_key='others' AND (strpos(message, '"+keyword+"') >=0 OR strpos(description, '"+keyword+"'))";
            JSONArray jsonArray = facebook.executeFQL(query);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject;
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    System.out.println(jsonObject.toString());
                } catch (JSONException ex) {}
            }
            return resultPosts;
        } catch (FacebookException e) {
            e.printStackTrace();
            return resultPosts;
        }
    }*/
}