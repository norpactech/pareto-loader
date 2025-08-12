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
import com.norpactech.pf.loader.dto.ProjectComponentDeleteApiRequest;
import com.norpactech.pf.loader.dto.ProjectComponentPutApiRequest;
import com.norpactech.pf.loader.dto.ProjectComponentPostApiRequest;

import com.norpactech.pf.loader.model.ProjectComponent;

public class ProjectComponentRepository extends ParetoNativeRepository<ProjectComponent> {
  
  private static final String RELATIVE_URL = "/project-component";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public ProjectComponent get(UUID id) throws Exception {
    return findOne(ProjectComponent.class, new HashMap<>(Map.of("id", id)));
  }

  public ProjectComponent findOne(UUID idProject, String name) throws Exception {
    return findOne(ProjectComponent.class, new HashMap<>(Map.of("idProject", idProject, "name", name)));
  }
  
  public ApiResponse save(ProjectComponentPostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(ProjectComponentPutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(ProjectComponentDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}