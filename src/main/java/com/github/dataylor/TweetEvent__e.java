package com.github.dataylor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TweetEvent__e {

    @JsonProperty(value="Name")
    String name;

    @JsonProperty(value="Screen_Name__c")
    private String screen_Name__c;

    @JsonProperty(value="Tweet_Publish_Date__c")
    private Date tweet_Publish_Date__c;

    @JsonProperty(value="Tweet_Text__c")
    private String tweet_Text__c;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreen_Name__c() {
        return screen_Name__c;
    }

    public void setScreen_Name__c(String screen_Name__c) {
        this.screen_Name__c = screen_Name__c;
    }

    public Date getTweet_Publish_Date__c() {
        return tweet_Publish_Date__c;
    }

    public void setTweet_Publish_Date__c(Date tweet_Publish_Date__c) {
        this.tweet_Publish_Date__c = tweet_Publish_Date__c;
    }

    public String getTweet_Text__c() {
        return tweet_Text__c;
    }

    public void setTweet_Text__c(String tweet_Text__c) {
        this.tweet_Text__c = tweet_Text__c;
    }
}