package com.norpactech.pf.loader.repository;
/**
 * © 2025 Northern Pacific Technologies, LLC. All Rights Reserved. 
 *  
 * For license details, see the LICENSE file in this project root.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.norpactech.pf.utils.ApiResponse;
import com.norpactech.pf.loader.dto.ProjectComponentPropertyPostApiRequest;
import com.norpactech.pf.loader.dto.ProjectComponentPropertyPutApiRequest;
import com.norpactech.pf.loader.dto.ProjectComponentPropertyDeleteApiRequest;

import com.norpactech.pf.loader.model.ProjectComponentProperty;

public class ProjectComponentPropertyRepository extends ParetoNativeRepository<ProjectComponentProperty> {
  
  private static final String RELATIVE_URL = "/project-component-property";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public ProjectComponentProperty get(UUID id) throws Exception {
    return findOne(ProjectComponentProperty.class, new HashMap<>(Map.of("id", id)));
  }

  public ProjectComponentProperty findOne(UUID id_project_component, String data_object_filter, String property_filter) throws Exception {
    return findOne(ProjectComponentProperty.class, new HashMap<>(Map.of("id_project_component", id_project_component, "data_object_filter", data_object_filter, "property_filter", property_filter)));
  }
  
  public ApiResponse save(ProjectComponentPropertyPostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(ProjectComponentPropertyPutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(ProjectComponentPropertyDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}