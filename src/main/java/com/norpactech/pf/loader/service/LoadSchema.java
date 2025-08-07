package com.norpactech.pf.loader.service;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.norpactech.pf.loader.dto.SchemaDeleteApiRequest;
import com.norpactech.pf.loader.dto.SchemaPostApiRequest;
import com.norpactech.pf.loader.dto.SchemaPutApiRequest;
import com.norpactech.pf.loader.model.Schema;
import com.norpactech.pf.loader.model.Tenant;
import com.norpactech.pf.loader.utils.ApiResponse;
import com.norpactech.pf.loader.utils.Constant;
import com.norpactech.pf.loader.utils.TextUtils;

public class LoadSchema extends BaseLoader {

  private static final Logger logger = LoggerFactory.getLogger(LoadSchema.class);

  public LoadSchema(String filePath, String fileName) throws Exception {
    super(filePath, fileName);
  }
  
  public void load(String filePath) throws Exception {
    
    logger.info("Beginning Schema Load from: " + getFullPath());
    int persisted = 0;
    int deleted = 0;
    int errors = 0;

    try {
      for (CSVRecord csvRecord : this.getCsvParser()) {
        if (isComment(csvRecord)) {
          continue;
        }
        var action = TextUtils.toString(csvRecord.get("action")).toLowerCase();
        var tenantName = TextUtils.toString(csvRecord.get("tenant"));
        var name = TextUtils.toString(csvRecord.get("name"));
        var description = TextUtils.toString(csvRecord.get("description"));
        var database = TextUtils.toString(csvRecord.get("database"));
        
        Tenant tenant = tenantRepository.findOne(tenantName);
        if (tenant == null) {
          logger.error("Tenant {} not found. Ignoring Schema {}.", tenantName, name);
          continue;
        }
        Schema schema = schemaRepository.findOne(tenant.getId(), name);
        ApiResponse response = null; 
            
        if (action.startsWith("p")) {
          if (schema == null) {
            SchemaPostApiRequest request = new SchemaPostApiRequest();
            request.setIdTenant(tenant.getId());
            request.setName(name);
            request.setDescription(description);
            request.setDatabase(database);
            request.setCreatedBy(Constant.THIS_PROCESS_CREATED);
            response = schemaRepository.save(request);
          }
          else {
            SchemaPutApiRequest request = new SchemaPutApiRequest();
            request.setId(schema.getId());
            request.setName(name);
            request.setDescription(description);
            request.setDatabase(database);
            request.setUpdatedAt(schema.getUpdatedAt());
            request.setUpdatedBy(Constant.THIS_PROCESS_UPDATED);
            response = schemaRepository.save(request);
          }
          if (response.getData() == null) {
            logger.error(this.getClass().getName() + " failed for: " + tenantName + " " + response.getMeta().getDetail());
            errors++;
          }
          else {
            persisted++;
          }
        }
        else if (action.startsWith("d") && schema != null) {
          SchemaDeleteApiRequest request = new SchemaDeleteApiRequest();
          request.setId(schema.getId());
          request.setUpdatedAt(schema.getUpdatedAt());
          request.setUpdatedBy(Constant.THIS_PROCESS_DELETED);
          schemaRepository.delete(request);
          deleted++;
        }
      }
    }
    catch (Exception e) {
      e.getStackTrace();
      throw e;
    }
    finally {
      if (this.getCsvParser() != null) this.getCsvParser().close();
    }
    logger.info("Completed Schema Load with {} persisted, {} deleted, and {} errors", persisted, deleted, errors);
  }
}  