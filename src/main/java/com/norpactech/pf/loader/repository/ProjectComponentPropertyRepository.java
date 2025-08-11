package com.norpactech.pf.loader.repository;
/**
 * © 2025 Northern Pacific Technologies, LLC. All Rights Reserved. 
 *  
 * For license details, see the LICENSE file in this project root.
 */

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