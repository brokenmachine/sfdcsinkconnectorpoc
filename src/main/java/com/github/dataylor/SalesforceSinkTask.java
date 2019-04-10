package com.github.dataylor;

import com.force.api.ApiConfig;
import com.force.api.ForceApi;
import com.github.jcustenborder.kafka.connect.utils.VersionUtil;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;


import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.data.Struct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SalesforceSinkTask extends SinkTask {

  static final Logger log = LoggerFactory.getLogger(SalesforceSinkTask.class);
  final ConcurrentLinkedDeque<SourceRecord> messageQueue = new ConcurrentLinkedDeque<>();
  SalesforceSinkConfig config;

  @Override
  public void start(Map<String, String> settings) {
      /* this method is run when the connector is started (on Kafka startup + any manual restarts
         if this throws an exception, the the task must be restarted manually to try again */
      this.config = new SalesforceSinkConfig(settings);
      log.info("Successfully started SalesforceSinkTask.");

  }

  @Override
  public void put(Collection<SinkRecord> sinkRecords) {
    for (SinkRecord record : sinkRecords) {
        Struct recordValue = (Struct)record.value(); // convert sink record to Kafka Connect Struct

        // get all the fields we are interested in
        String tweetText= recordValue.getString("Text");
        tweetText = tweetText.substring(0, Math.min(tweetText.length(), 255)); // Tweets can be 280 chars now, who knew?

        Date publishDate = (Date)recordValue.get("CreatedAt");
        String twitterUserName = recordValue.getStruct("User").getString("ScreenName");
        log.info("Tweet values: "+twitterUserName+':'+publishDate+":"+tweetText);

        ForceApi api = new ForceApi(new ApiConfig()
              .setUsername(config.username()) // username of integration user
              .setPassword(config.password()+config.passwordToken()) // password + security token
              .setClientId(config.consumerKey()) // client key from OAuth Connected App
              .setClientSecret(config.consumerSecret()));  // client secret from OAuth Connected App

        // create the platform event (REST API under the covers)
        TweetEvent__e tweet = new TweetEvent__e();
        tweet.setScreen_Name__c(twitterUserName);
        tweet.setTweet_Text__c(tweetText); // Tweets can be 280 chars long now, who knew?
        tweet.setTweet_Publish_Date__c(publishDate);

        String id = api.createSObject("TweetEvent__e", tweet);
        log.info("Called REST API to create TweetEvent__e platform event record.  Id: "+id);
    }
  }

  @Override
  public void flush(Map<TopicPartition, OffsetAndMetadata> map) {

  }

  @Override
  public void stop() {

  }

  @Override
  public String version() {
    return VersionUtil.version(this.getClass());
  }
}
