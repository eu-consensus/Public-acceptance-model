/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Crawlers;

import Entities.CustomStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author ViP
 */
public class TwitterCrawler {

    Twitter twitter;

    public TwitterCrawler() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("7JmKYPf5FT6pNsv4buVTKA")
                .setOAuthConsumerSecret("vt0FQ5SE5C6UY18fqmHL7Hbfkdnj5cAF3dAYVMP0")
                .setOAuthAccessToken("359447855-oE9GMIqbe8zCqJeklXvxYWPqS6kP7DJD98KQ56fI")
                .setOAuthAccessTokenSecret("FWnVVSPqk30W0htd5QXU5PXZsR8VgSKWngsLu7WycBjRF");
        twitter = new TwitterFactory(cb.build()).getInstance();
    }

    public List<CustomStatus> search(String keyword) {
        Query query = new Query(keyword);
        query.count(100);
        QueryResult qr;
        try {
            List<Status> qrTweets;
            qr = twitter.search(query);
            qrTweets = qr.getTweets();
            try {
                for (int i = 0; i < 149; i++) {
                    query = qr.nextQuery();
                    qr = twitter.search(query);
                    qrTweets.addAll(qr.getTweets());
                }
            } catch (Exception exe) {
                //exe.printStackTrace();
            }
            //System.out.println(qr.getRateLimitStatus().getRemaining());
            List<CustomStatus> result=new ArrayList<CustomStatus>();
            for(int i=0;i<qrTweets.size();i++){
                result.add(new CustomStatus(qrTweets.get(i)));
            }
            return result;
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<CustomStatus> smallsearch(String keyword) {
        Query query = new Query(keyword);
        query.count(30);
        QueryResult qr;
        try {
            List<Status> qrTweets;
            qr = twitter.search(query);
            qrTweets = qr.getTweets();
            List<CustomStatus> result=new ArrayList<CustomStatus>();
            for(int i=0;i<qrTweets.size();i++){
                result.add(new CustomStatus(qrTweets.get(i)));
            }
            return result;
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public CustomStatus getById(String text){
        try {
            CustomStatus cs=new CustomStatus(this.twitter.showStatus(Long.parseLong(text)));
            System.out.println(cs.getText());
            return cs;
        } catch (TwitterException ex) {
            Logger.getLogger(TwitterCrawler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
