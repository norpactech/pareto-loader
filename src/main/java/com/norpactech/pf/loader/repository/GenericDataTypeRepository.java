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
import com.norpactech.pf.loader.dto.GenericDataTypePutApiRequest;
import com.norpactech.pf.loader.dto.GenericDataTypePostApiRequest;
import com.norpactech.pf.loader.dto.GenericDataTypeDeleteApiRequest;

import com.norpactech.pf.loader.model.GenericDataType;

public class GenericDataTypeRepository extends ParetoNativeRepository<GenericDataType> {
  
  private static final String RELATIVE_URL = "/generic-data-type";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public GenericDataType get(UUID id) throws Exception {
    return findOne(GenericDataType.class, new HashMap<>(Map.of("id", id)));
  }

  public GenericDataType findOne(UUID id_tenant, String name) throws Exception {
    return findOne(GenericDataType.class, new HashMap<>(Map.of("id_tenant", id_tenant, "name", name)));
  }
  
  public ApiResponse save(GenericDataTypePostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(GenericDataTypePutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(GenericDataTypeDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}