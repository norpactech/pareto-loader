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
import com.norpactech.pf.loader.dto.ContextDeleteApiRequest;
import com.norpactech.pf.loader.dto.ContextPostApiRequest;
import com.norpactech.pf.loader.dto.ContextPutApiRequest;

import com.norpactech.pf.loader.model.Context;

public class ContextRepository extends ParetoNativeRepository<Context> {
  
  private static final String RELATIVE_URL = "/context";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public Context get(UUID id) throws Exception {
    return findOne(Context.class, new HashMap<>(Map.of("id", id)));
  }

  public Context findOne(String name) throws Exception {
    return findOne(Context.class, new HashMap<>(Map.of("name", name)));
  }
  
  public ApiResponse save(ContextPostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(ContextPutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(ContextDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}