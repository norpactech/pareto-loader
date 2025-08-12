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
import com.norpactech.pf.loader.dto.ProjectDeleteApiRequest;
import com.norpactech.pf.loader.dto.ProjectPutApiRequest;
import com.norpactech.pf.loader.dto.ProjectPostApiRequest;

import com.norpactech.pf.loader.model.Project;

public class ProjectRepository extends ParetoNativeRepository<Project> {
  
  private static final String RELATIVE_URL = "/project";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public Project get(UUID id) throws Exception {
    return findOne(Project.class, new HashMap<>(Map.of("id", id)));
  }

  public Project findOne(UUID id_schema, String name) throws Exception {
    return findOne(Project.class, new HashMap<>(Map.of("id_schema", id_schema, "name", name)));
  }
  
  public ApiResponse save(ProjectPostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(ProjectPutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(ProjectDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}