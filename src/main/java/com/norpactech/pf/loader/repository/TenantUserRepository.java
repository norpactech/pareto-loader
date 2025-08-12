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
import com.norpactech.pf.loader.dto.TenantUserDeleteApiRequest;
import com.norpactech.pf.loader.dto.TenantUserPostApiRequest;

import com.norpactech.pf.loader.model.TenantUser;

public class TenantUserRepository extends ParetoNativeRepository<TenantUser> {
  
  private static final String RELATIVE_URL = "/tenant-user";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public TenantUser get(UUID id_tenant, UUID id_user) throws Exception {
    return findOne(TenantUser.class, new HashMap<>(Map.of("id_tenant", id_tenant, "id_user", id_user)));
  }
  
  public ApiResponse save(TenantUserPostApiRequest request) throws Exception {
    return post(toParams(request));
  }  

  public ApiResponse delete(TenantUserDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}