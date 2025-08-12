package com.norpactech.pf.loader.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadAll {

  private static final Logger logger = LoggerFactory.getLogger(LoadAll.class);
  
  public static void load(String filePath) throws Exception {
    
    logger.info("Beginning Load All from: {}", filePath);
    new LoadTenant(filePath, "Tenant.csv").load(filePath);
    new LoadSchema(filePath, "Schema.csv").load(filePath);
    new LoadUser(filePath, "User.csv").load(filePath); // with TenantUser
    new LoadValidation(filePath, "Validation.csv").load(filePath);
    new LoadGenericDataType(filePath, "GenericDataType.csv").load(filePath);
    new LoadGenericDataTypeAttribute(filePath, "GenericDataTypeAttribute.csv").load(filePath);
//    new LoadContextDataType(filePath, "ContextDataType.csv").load(filePath);
    
    /*
      ContextPropertyTypeETL contextPropertyTypeETL;
      GenericPropertyTypeETL genericPropertyTypeETL;
      ProjectETL projectETL;
      ProjectComponentETL projectComponentETL;
      ProjectComponentPropertyETL projectComponentPropertyETL;
    */

    
    
    logger.info("Completed Load All");
  }
}  