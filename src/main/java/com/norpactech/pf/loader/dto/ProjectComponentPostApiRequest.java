package com.norpactech.pf.loader.dto;
/**
 * © 2025 Northern Pacific Technologies, LLC. All Rights Reserved. 
 *  
 * For license details, see the LICENSE file in this project root.
 */
import java.lang.String;
import java.util.Map;
import java.util.UUID;
import java.util.LinkedHashMap;
import io.swagger.v3.oas.annotations.Hidden;

/**
 * Post API Request
 * 
 * Project Component (i.e. DDL)
 */
 public class ProjectComponentPostApiRequest {

  private UUID idProject;
  private UUID idPlugin;
  private UUID idContext;
  private String name;
  private String description;
  private String subPackage;
  private String createdBy;

  public ProjectComponentPostApiRequest () {}

  @Hidden
  public Map<String, Object> getInsertRequest() {
    
    var request = new LinkedHashMap<String, Object>();
    request.put("sql", "SELECT pareto.i_project_component(?,?,?,?,?,?,?)");
    request.put("idProject", this.idProject);
    request.put("idPlugin", this.idPlugin);
    request.put("idContext", this.idContext);
    request.put("name", this.name);
    request.put("description", this.description);
    request.put("subPackage", this.subPackage);
    request.put("createdBy", this.createdBy);
    return request;
  }
  
  // Getters and Setters...
    
  public UUID getIdProject() {
    return this.idProject;
  }
    
  public UUID setIdProject(UUID idProject) {
    return this.idProject = idProject;
  }    
    
  public UUID getIdPlugin() {
    return this.idPlugin;
  }
    
  public UUID setIdPlugin(UUID idPlugin) {
    return this.idPlugin = idPlugin;
  }    
    
  public UUID getIdContext() {
    return this.idContext;
  }
    
  public UUID setIdContext(UUID idContext) {
    return this.idContext = idContext;
  }    
    
  public String getName() {
    return this.name;
  }
    
  public String setName(String name) {
    return this.name = name;
  }    
    
  public String getDescription() {
    return this.description;
  }
    
  public String setDescription(String description) {
    return this.description = description;
  }    
    
  public String getSubPackage() {
    return this.subPackage;
  }
    
  public String setSubPackage(String subPackage) {
    return this.subPackage = subPackage;
  }    
    
  public String getCreatedBy() {
    return this.createdBy;
  }
    
  public String setCreatedBy(String createdBy) {
    return this.createdBy = createdBy;
  }    
}