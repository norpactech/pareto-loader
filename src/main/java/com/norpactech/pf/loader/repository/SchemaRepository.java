package com.norpactech.pf.loader.repository;
/**
 * © 2025 Northern Pacific Technologies, LLC. All Rights Reserved. 
 *  
 * For license details, see the LICENSE file in this project root.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.norpactech.pf.loader.dto.SchemaDeleteApiRequest;
import com.norpactech.pf.loader.dto.SchemaPostApiRequest;
import com.norpactech.pf.loader.dto.SchemaPutApiRequest;
import com.norpactech.pf.loader.model.Schema;
import com.norpactech.pf.loader.utils.ApiResponse;

public class SchemaRepository extends ParetoApiRepository<Schema> {

  private static final String RELATIVE_URL = "/schema";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public Schema findOne(UUID idTenant, String name) throws Exception {
    return findOne(Schema.class, new HashMap<>(Map.of("idTenant", idTenant.toString(), "name", name)));
  } 
  
  public ApiResponse save(SchemaPostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(SchemaPutApiRequest request) throws Exception {
    return put(toParams(request));
  } 
  
  public ApiResponse delete(SchemaDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }      
}
