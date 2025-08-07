package com.norpactech.pf.loader.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadAll {

  private static final Logger logger = LoggerFactory.getLogger(LoadAll.class);
  
  public static void load(String filePath) throws Exception {
    
    logger.info("Beginning Load All");
    new LoadTenant(filePath, "Tenant.csv").load(filePath);
    new LoadSchema(filePath, "Schema.csv").load(filePath);
    
    logger.info("Completed Load All");
  }
}  