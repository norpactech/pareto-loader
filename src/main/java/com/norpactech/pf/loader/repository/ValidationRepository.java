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
import com.norpactech.pf.loader.dto.ValidationDeleteApiRequest;
import com.norpactech.pf.loader.dto.ValidationPutApiRequest;
import com.norpactech.pf.loader.dto.ValidationPostApiRequest;

import com.norpactech.pf.loader.model.Validation;

public class ValidationRepository extends ParetoNativeRepository<Validation> {
  
  private static final String RELATIVE_URL = "/validation";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public Validation get(UUID id) throws Exception {
    return findOne(Validation.class, new HashMap<>(Map.of("id", id)));
  }

  public Validation findOne(UUID id_tenant, String name) throws Exception {
    return findOne(Validation.class, new HashMap<>(Map.of("id_tenant", id_tenant, "name", name)));
  }
  
  public ApiResponse save(ValidationPostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(ValidationPutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(ValidationDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}