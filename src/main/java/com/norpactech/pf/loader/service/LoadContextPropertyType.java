package com.norpactech.pf.loader.service;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.norpactech.pf.loader.dto.ContextPropertyTypePostApiRequest;
import com.norpactech.pf.loader.dto.ContextPropertyTypePutApiRequest;
import com.norpactech.pf.loader.dto.UserDeleteApiRequest;
import com.norpactech.pf.utils.ApiResponse;
import com.norpactech.pf.utils.Constant;
import com.norpactech.pf.utils.TextUtils;

public class LoadContextPropertyType extends BaseLoader {

  private static final Logger logger = LoggerFactory.getLogger(LoadContextPropertyType.class);

  public LoadContextPropertyType(String filePath, String fileName) throws Exception {
    super(filePath, fileName);
  }
  
  public void load(String filePath) throws Exception {
    
    logger.info("Beginning User Load from: " + getFullPath());
    int persisted = 0;
    int deleted = 0;
    int errors = 0;

    try {
      for (CSVRecord csvRecord : this.getCsvParser()) {
        if (isComment(csvRecord)) {
          continue;
        }
        var action = TextUtils.toString(csvRecord.get("action")).toLowerCase();
        var contextName = TextUtils.toString(csvRecord.get("context"));
        var tenantName = TextUtils.toString(csvRecord.get("tenant"));
        var schemaName = TextUtils.toString(csvRecord.get("schema"));        
        var genericDataTypeName = TextUtils.toString(csvRecord.get("generic_data_type"));
        var genericPropertyTypeName = TextUtils.toString(csvRecord.get("generic_property_type"));
        var length = TextUtils.toInteger(csvRecord.get("length"));            
        var scale = TextUtils.toInteger(csvRecord.get("scale"));            
        var isNullable = TextUtils.toBoolean(csvRecord.get("is_nullable"));            
        var defaultValue = TextUtils.toString(csvRecord.get("default_value"));            

        var tenant = tenantRepository.findOne(tenantName);
        if (tenant == null) {
          logger.error("Tenant {} not found. Ignoring ContextPropertyType.", tenantName);
          continue;
        }

        var schema = schemaRepository.findOne(tenant.getId(), schemaName);
        if (schema == null) {
          logger.error("Schema {} not found. Ignoring ContextPropertyType {}.", schemaName);
          continue;
        }        

        var context = contextRepository.findOne(contextName);
        if (context == null) {
          logger.error("Context {} not found. Ignoring ContextPropertyType {}.", contextName);
          continue;
        }

        var genericDataType = genericDataTypeRepository.findOne(tenant.getId(), genericDataTypeName);
        if (genericDataType == null) {
          logger.error("Generic Data Type {} not found. Ignoring ContextPropertyType {}.", genericDataTypeName);
          continue;
        }
        
        var genericPropertyType = genericPropertyTypeRepository.findOne(genericDataType.getId(), genericPropertyTypeName);
        if (genericPropertyType == null) {
          logger.error("Generic Property Type {} not found. Ignoring ContextPropertyType {}.", genericPropertyTypeName);
          continue;
        }
        var contextPropertyType = contextPropertyTypeRepository.findOne(context.getId(), genericPropertyType.getId());
        ApiResponse response = null; 
        
        if (action.startsWith("p")) {
          if (contextPropertyType == null) {
            var request = new ContextPropertyTypePostApiRequest();
            request.setIdContext(context.getId());
            request.setIdGenericPropertyType(genericPropertyType.getId());
            request.setLength(length);            
            request.setScale(scale);            
            request.setIsNullable(isNullable);            
            request.setDefaultValue(defaultValue); 
            request.setCreatedBy(Constant.THIS_PROCESS_CREATED);
            response = contextPropertyTypeRepository.save(request);                    
          }
          else {
            var request = new ContextPropertyTypePutApiRequest();
            request.setId(contextPropertyType.getId());
            request.setIdGenericPropertyType(genericPropertyType.getId());
            request.setLength(length);            
            request.setScale(scale);            
            request.setIsNullable(isNullable);            
            request.setDefaultValue(defaultValue); 
            request.setUpdatedAt(contextPropertyType.getUpdatedAt());
            request.setUpdatedBy(Constant.THIS_PROCESS_UPDATED);
            response = contextPropertyTypeRepository.save(request);
          }
          if (response.getData() == null) {
            if (response.getError() != null) {
              logger.error(response.getError().toString());
            }
            else {
              logger.error(this.getClass().getName() + " failed " + response.getMeta().getDetail());
            }
            errors++;
          }
          else {
            persisted++;
          }          
        }
        else if (action.startsWith("d") && contextPropertyType != null) {
          var request = new UserDeleteApiRequest();
          request.setId(contextPropertyType.getId());
          request.setUpdatedAt(contextPropertyType.getUpdatedAt());
          request.setUpdatedBy(Constant.THIS_PROCESS_DELETED);
          userRepository.delete(request);
          deleted++;
        }
      }
    }
    catch (Exception e) {
      logger.error("Error loading users", e);
      throw e;
    }
    finally {
      if (this.getCsvParser() != null) this.getCsvParser().close();
    }
    logger.info("Completed User Load with {} persisted, {} deleted, and {} errors", persisted, deleted, errors);
  }
}