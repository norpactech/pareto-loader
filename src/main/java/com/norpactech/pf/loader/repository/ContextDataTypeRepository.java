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
import com.norpactech.pf.loader.dto.ContextDataTypePostApiRequest;
import com.norpactech.pf.loader.dto.ContextDataTypeDeleteApiRequest;
import com.norpactech.pf.loader.dto.ContextDataTypePutApiRequest;

import com.norpactech.pf.loader.model.ContextDataType;

public class ContextDataTypeRepository extends ParetoNativeRepository<ContextDataType> {
  
  private static final String RELATIVE_URL = "/context-data-type";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public ContextDataType get(UUID id) throws Exception {
    return findOne(ContextDataType.class, new HashMap<>(Map.of("id", id)));
  }

  public ContextDataType findOne(UUID id_context, String name) throws Exception {
    return findOne(ContextDataType.class, new HashMap<>(Map.of("id_context", id_context, "name", name)));
  }
  
  public ApiResponse save(ContextDataTypePostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(ContextDataTypePutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(ContextDataTypeDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}