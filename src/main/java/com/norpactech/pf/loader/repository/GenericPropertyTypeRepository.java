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
import com.norpactech.pf.loader.dto.GenericPropertyTypePutApiRequest;
import com.norpactech.pf.loader.dto.GenericPropertyTypePostApiRequest;
import com.norpactech.pf.loader.dto.GenericPropertyTypeDeleteApiRequest;

import com.norpactech.pf.loader.model.GenericPropertyType;

public class GenericPropertyTypeRepository extends ParetoNativeRepository<GenericPropertyType> {
  
  private static final String RELATIVE_URL = "/generic-property-type";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public GenericPropertyType get(UUID id) throws Exception {
    return findOne(GenericPropertyType.class, new HashMap<>(Map.of("id", id)));
  }

  public GenericPropertyType findOne(UUID id_generic_data_type, String name) throws Exception {
    return findOne(GenericPropertyType.class, new HashMap<>(Map.of("id_generic_data_type", id_generic_data_type, "name", name)));
  }
  
  public ApiResponse save(GenericPropertyTypePostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(GenericPropertyTypePutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(GenericPropertyTypeDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}