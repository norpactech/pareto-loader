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
import com.norpactech.pf.loader.dto.GenericDataTypeAttributePostApiRequest;
import com.norpactech.pf.loader.dto.GenericDataTypeAttributePutApiRequest;
import com.norpactech.pf.loader.dto.GenericDataTypeAttributeDeleteApiRequest;

import com.norpactech.pf.loader.model.GenericDataTypeAttribute;

public class GenericDataTypeAttributeRepository extends ParetoNativeRepository<GenericDataTypeAttribute> {
  
  private static final String RELATIVE_URL = "/generic-data-type-attribute";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public GenericDataTypeAttribute findOne(UUID id_generic_data_type, String name) throws Exception {
    return findOne(GenericDataTypeAttribute.class, new HashMap<>(Map.of("id_generic_data_type", id_generic_data_type, "name", name)));
  }
  
  public ApiResponse save(GenericDataTypeAttributePostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(GenericDataTypeAttributePutApiRequest request) throws Exception {
    return put(toParams(request));
  } 
  public ApiResponse delete(GenericDataTypeAttributeDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}