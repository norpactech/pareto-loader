package com.norpactech.pf.loader.service;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.norpactech.pf.loader.dto.ProjectComponentPostApiRequest;
import com.norpactech.pf.loader.dto.ProjectComponentPutApiRequest;
import com.norpactech.pf.loader.dto.ProjectDeleteApiRequest;
import com.norpactech.pf.utils.ApiResponse;
import com.norpactech.pf.utils.Constant;
import com.norpactech.pf.utils.TextUtils;

public class LoadProjectComponent extends BaseLoader {

  private static final Logger logger = LoggerFactory.getLogger(LoadProjectComponent.class);

  public LoadProjectComponent(String filePath, String fileName) throws Exception {
    super(filePath, fileName);
  }
  
  public void load() throws Exception {
    
    if (!isFileAvailable()) return;

    logger.info("Beginning Project Component Load from: " + getFullPath());
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
        var schemaName = TextUtils.toString(csvRecord.get("schema"));
        var projectName = TextUtils.toString(csvRecord.get("project"));
        var contextName = TextUtils.toString(csvRecord.get("context"));
        var pluginName = TextUtils.toString(csvRecord.get("plugin"));
        var name = TextUtils.toString(csvRecord.get("name"));
        var description = TextUtils.toString(csvRecord.get("description"));
        var subPackage = TextUtils.toString(csvRecord.get("sub_package"));
        
        var tenant = tenantRepository.findOne(tenantName);
        if (tenant == null) {
          logger.error("Tenant {} not found. Ignoring Project Component {}.", tenantName, name);
          continue;
        }
        
        var schema = schemaRepository.findOne(tenant.getId(), schemaName);
        if (schema == null) {
          logger.error("Schema {} not found. Ignoring Project Component {}.", schemaName, name);
          continue;
        }

        var project = projectRepository.findOne(schema.getId(), projectName);
        if (project == null) {
          logger.error("Project {} not found. Ignoring Project Component {}.", projectName, name);
          continue;
        }

        var context = contextRepository.findOne(contextName);
        if (context == null) {
          logger.error("Context {} not found. Ignoring Project Component {}.", contextName, name);
          continue;
        }
        
        var plugin = pluginRepository.findOne(context.getId(), pluginName);
        if (plugin == null) {
          logger.error("Plugin {} not found. Ignoring Project Component {}.", pluginName, name);
          continue;
        }
        var projectComponent = projectComponentRepository.findOne(project.getId(), name);        
        ApiResponse response = null; 

        if (action.startsWith("p")) {
          if (projectComponent == null) {
            var request = new ProjectComponentPostApiRequest();
            request.setIdProject(project.getId());
            request.setIdContext(context.getId());
            request.setIdPlugin(plugin.getId());
            request.setName(name);
            request.setDescription(description);
            request.setSubPackage(subPackage);
            request.setCreatedBy(Constant.THIS_PROCESS_CREATED);
            response = projectComponentRepository.save(request);
          }
          else {
            var request = new ProjectComponentPutApiRequest();
            request.setId(projectComponent.getId());
            request.setIdContext(context.getId());
            request.setIdPlugin(plugin.getId());
            request.setName(name);
            request.setDescription(description);
            request.setSubPackage(subPackage);
            request.setDescription(description);
            request.setSubPackage(subPackage);
            request.setUpdatedAt(projectComponent.getUpdatedAt());
            request.setUpdatedBy(Constant.THIS_PROCESS_UPDATED);
            response = projectComponentRepository.save(request);
          }
          if (response.getData() == null) {
            logger.error(this.getClass().getName() + " failed for: " + tenantName + " " + response.getMeta().getDetail());
            errors++;
          }
          else {
            persisted++;
          }
        }
        else if (action.startsWith("d") && projectComponent != null) {
          ProjectDeleteApiRequest request = new ProjectDeleteApiRequest();
          request.setId(projectComponent.getId());
          request.setUpdatedAt(projectComponent.getUpdatedAt());
          request.setUpdatedBy(Constant.THIS_PROCESS_DELETED);
          projectRepository.delete(request);
          deleted++;
        }
      }
    }
    catch (Exception e) {
      logger.error("Error Loading Project Component {}", e.getMessage());
      throw e;
    }
    finally {
      if (this.getCsvParser() != null) this.getCsvParser().close();
    }
    logger.info("Completed Project Component Load with {} persisted, {} deleted, and {} errors", persisted, deleted, errors);
  }
}  