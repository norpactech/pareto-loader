package com.norpactech.pf.loader.service;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.norpactech.pf.loader.dto.TenantPostApiRequest;
import com.norpactech.pf.loader.dto.TenantPutApiRequest;
import com.norpactech.pf.loader.model.Tenant;
import com.norpactech.pf.loader.utils.ApiResponse;
import com.norpactech.pf.loader.utils.Constant;
import com.norpactech.pf.loader.utils.TextUtils;

public class LoadTenant extends BaseLoader {

  private static final Logger logger = LoggerFactory.getLogger(LoadTenant.class);

  public LoadTenant(String filePath, String fileName) throws Exception {
    super(filePath, fileName);
  }
  
  public void load(String filePath) throws Exception {
    
    logger.info("Beginning Tenant Load from: " + getFullPath());

    try {
      for (CSVRecord csvRecord : this.getCsvParser()) {
        if (isComment(csvRecord)) {
          continue;
        }
        String action = csvRecord.get("action").toLowerCase();
        String tenantName = TextUtils.toString(csvRecord.get("name"));
        String description = TextUtils.toString(csvRecord.get("description"));
        String copyright = TextUtils.toString(csvRecord.get("copyright"));
        String timeZone = TextUtils.toString(csvRecord.get("time_zone"));
        
        Tenant tenant = tenantRepository.findOne(tenantName);
        ApiResponse response = null; 
            
        if (action.startsWith("p")) {
          if (tenant == null) {
            TenantPostApiRequest request = new TenantPostApiRequest();
            request.setName(tenantName);
            request.setDescription(description);
            request.setCopyright(copyright);
            request.setTimeZone(timeZone);
            request.setCreatedBy(Constant.THIS_PROCESS_CREATED);
            response = tenantRepository.save(request);
          }
          else {
            TenantPutApiRequest request = new TenantPutApiRequest();
            request.setId(tenant.getId());
            request.setName(tenantName);
            request.setDescription(description);
            request.setCopyright(copyright);
            request.setTimeZone(timeZone);
            request.setUpdatedAt(tenant.getUpdatedAt());
            request.setUpdatedBy(Constant.THIS_PROCESS_UPDATED);
            response = tenantRepository.save(request);
          }
          if (response.getData() == null) {
            logger.error(this.getClass().getName() + " failed for: " + tenantName);
          }
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
    logger.info("Completed Tenant Load");
  }
}  