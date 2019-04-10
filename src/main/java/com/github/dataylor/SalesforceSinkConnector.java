package com.github.dataylor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jcustenborder.kafka.connect.utils.VersionUtil;
import com.github.jcustenborder.kafka.connect.utils.config.*;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */

@Description("This is a description of this connector and will show up in the documentation")
@DocumentationImportant("This is a important information that will show up in the documentation.")
@DocumentationTip("This is a tip that will show up in the documentation.")
@Title("Acumen Salesforce Sink Connector") //This is the display name that will show up in the documentation.
@DocumentationNote("This is a note that will show up in the documentation")
public class SalesforceSinkConnector extends SinkConnector {

  private static Logger log = LoggerFactory.getLogger(SalesforceSinkConnector.class);
  private SalesforceSinkConfig config;
  private Map<String, String> configProperties;

  @Override
  public List<Map<String, String>> taskConfigs(int maxTasks) {
    /**
     * This method is used to schedule the number of tasks that will be running. This should not exceed maxTasks.
     * Here is a spot where you can dish out work. For example if you are reading from multiple tables
     * in a database, you can assign a table per task.
     */
    List<Map<String, String>> taskConfigs = new ArrayList<>();
    Map<String, String> taskProps = new HashMap<>();
    taskProps.putAll(configProperties);
    for (int i = 0; i < maxTasks; i++) {
      taskConfigs.add(taskProps);
    }
    return taskConfigs;

  }

  @Override
  public void start(Map<String, String> settings) {
    /**
     * This will be executed once per connector. This can be used to handle connector level setup. For
     * example if you are persisting state, you can use this to method to create your state table. You
     * could also use this to verify permissions
     */
    try {
      configProperties = settings;
      config = new SalesforceSinkConfig(settings);
    } catch (ConfigException e) {
      throw new ConnectException("Couldn't start SalesforceSinkConnectorConfig due to configuration error", e);
    }

  }

  @Override
  public void stop() {
     /* Do things that are necessary to stop your connector. */

  }

  @Override
  public ConfigDef config() {
    return SalesforceSinkConfig.conf();
  }

  @Override
  public Class<? extends Task> taskClass() {
    return SalesforceSinkTask.class;
  }

  public String version() {
    return VersionUtil.version(this.getClass());
  }
}
