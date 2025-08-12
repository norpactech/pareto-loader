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
import com.norpactech.pf.loader.dto.ContextPropertyTypePostApiRequest;
import com.norpactech.pf.loader.dto.ContextPropertyTypePutApiRequest;
import com.norpactech.pf.loader.dto.ContextPropertyTypeDeleteApiRequest;

import com.norpactech.pf.loader.model.ContextPropertyType;

public class ContextPropertyTypeRepository extends ParetoNativeRepository<ContextPropertyType> {
  
  private static final String RELATIVE_URL = "/context-property-type";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public ContextPropertyType get(UUID id) throws Exception {
    return findOne(ContextPropertyType.class, new HashMap<>(Map.of("id", id)));
  }

  public ContextPropertyType findOne(UUID id_context, UUID id_generic_property_type) throws Exception {
    return findOne(ContextPropertyType.class, new HashMap<>(Map.of("id_context", id_context, "id_generic_property_type", id_generic_property_type)));
  }
  
  public ApiResponse save(ContextPropertyTypePostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(ContextPropertyTypePutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(ContextPropertyTypeDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}