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
import com.norpactech.pf.loader.dto.RefTablesDeleteApiRequest;
import com.norpactech.pf.loader.dto.RefTablesPostApiRequest;
import com.norpactech.pf.loader.dto.RefTablesPutApiRequest;

import com.norpactech.pf.loader.model.RefTables;

public class RefTablesRepository extends ParetoNativeRepository<RefTables> {
  
  private static final String RELATIVE_URL = "/ref-tables";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public RefTables get(UUID id) throws Exception {
    return findOne(RefTables.class, new HashMap<>(Map.of("id", id)));
  }

  public RefTables findOne(UUID id_ref_table_type, String name) throws Exception {
    return findOne(RefTables.class, new HashMap<>(Map.of("id_ref_table_type", id_ref_table_type, "name", name)));
  }
  
  public ApiResponse save(RefTablesPostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(RefTablesPutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(RefTablesDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}