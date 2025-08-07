package com.norpactech.pf.loader.repository;
/**
 * © 2025 Northern Pacific Technologies, LLC. All Rights Reserved. 
 *  
 * For license details, see the LICENSE file in this project root.
 */
import java.util.HashMap;
import java.util.Map;

import com.norpactech.pf.loader.model.Tenant;

public class TenantRepository extends ParetoApiRepository<Tenant> {
  
  private static final String RELATIVE_URL = "/tenant";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public Tenant findOne(String name) throws Exception {
    return findOne(Tenant.class, new HashMap<>(Map.of("name", name)));
  }  
}
