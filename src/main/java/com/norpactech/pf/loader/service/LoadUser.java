package com.norpactech.pf.loader.service;
import java.time.LocalDateTime;

import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.norpactech.pf.loader.dto.UserDeleteApiRequest;
import com.norpactech.pf.loader.dto.UserPostApiRequest;
import com.norpactech.pf.loader.dto.UserPutApiRequest;
import com.norpactech.pf.utils.ApiResponse;
import com.norpactech.pf.utils.Constant;
import com.norpactech.pf.utils.TextUtils;

public class LoadUser extends BaseLoader {

  private static final Logger logger = LoggerFactory.getLogger(LoadUser.class);

  public LoadUser(String filePath, String fileName) throws Exception {
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
        String action = TextUtils.toString(csvRecord.get("action")).toLowerCase();
        String tenantName = TextUtils.toString(csvRecord.get("tenant"));
        String email = TextUtils.toString(csvRecord.get("email"));
        String lastName = TextUtils.toString(csvRecord.get("last_name"));
        String firstName = TextUtils.toString(csvRecord.get("first_name"));
        String phone = TextUtils.toString(csvRecord.get("phone"));
        String street1 = TextUtils.toString(csvRecord.get("street1"));
        String street2 = TextUtils.toString(csvRecord.get("street2"));
        String city = TextUtils.toString(csvRecord.get("city"));
        String state = TextUtils.toString(csvRecord.get("state"));
        String zipCode = TextUtils.toString(csvRecord.get("zip_code"));
        LocalDateTime termsAccepted = LocalDateTime.now();
        
        var tenant = tenantRepository.findOne(tenantName);
        if (tenant == null) {
          logger.error("Tenant {} not found. Ignoring Schema {}.", tenantName, email);
          continue;
        }
        var user = userRepository.findOne(email);
        ApiResponse response = null; 
        
        if (action.startsWith("p")) {
          if (user == null) {
            var request = new UserPostApiRequest();
            request.setEmail(email);
            request.setLastName(lastName);
            request.setFirstName(firstName);
            request.setPhone(phone);
            request.setStreet1(street1);
            request.setStreet2(street2);
            request.setCity(city);
            request.setState(state);
            request.setZipCode(zipCode);
            request.setTermsAccepted(termsAccepted);
            response = userRepository.save(request);                 
          }
          else {
            var request = new UserPutApiRequest();
            request.setLastName(lastName);
            request.setFirstName(firstName);
            request.setPhone(phone);
            request.setStreet1(street1);
            request.setStreet2(street2);
            request.setCity(city);
            request.setState(state);
            request.setZipCode(zipCode);
            response = userRepository.save(request);                 
          }
          if (response.getData() == null) {
            logger.error(this.getClass().getName() + " failed for: " + email + " " + response.getMeta().getDetail());
            errors++;
          }
          else {
            persisted++;
          }
        }
        else if (action.startsWith("d") && user != null) {
          var request = new UserDeleteApiRequest();
          request.setId(user.getId());
          request.setUpdatedAt(user.getUpdatedAt());
          request.setUpdatedBy(Constant.THIS_PROCESS_DELETED);
          userRepository.delete(request);
          deleted++;
        }
      }  // for
    }
    catch (Exception e) {
      e.getStackTrace();
      throw e;
    }
    finally {
      if (this.getCsvParser() != null) this.getCsvParser().close();
    }
    logger.info("Completed Tenant Load with {} persisted, {} deleted, and {} errors", persisted, deleted, errors);
  }
}