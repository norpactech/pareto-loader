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
import com.norpactech.pf.loader.dto.RefTableTypePutApiRequest;
import com.norpactech.pf.loader.dto.RefTableTypePostApiRequest;
import com.norpactech.pf.loader.dto.RefTableTypeDeleteApiRequest;

import com.norpactech.pf.loader.model.RefTableType;

public class RefTableTypeRepository extends ParetoNativeRepository<RefTableType> {
  
  private static final String RELATIVE_URL = "/ref-table-type";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public RefTableType get(UUID id) throws Exception {
    return findOne(RefTableType.class, new HashMap<>(Map.of("id", id)));
  }

  public RefTableType findOne(UUID id_tenant, String name) throws Exception {
    return findOne(RefTableType.class, new HashMap<>(Map.of("id_tenant", id_tenant, "name", name)));
  }
  
  public ApiResponse save(RefTableTypePostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(RefTableTypePutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(RefTableTypeDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}