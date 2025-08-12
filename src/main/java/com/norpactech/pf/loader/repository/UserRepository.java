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
import com.norpactech.pf.loader.dto.UserDeleteApiRequest;
import com.norpactech.pf.loader.dto.UserPostApiRequest;
import com.norpactech.pf.loader.dto.UserPutApiRequest;

import com.norpactech.pf.loader.model.User;

public class UserRepository extends ParetoNativeRepository<User> {
  
  private static final String RELATIVE_URL = "/user";

  @Override
  protected String getRelativeURL() {
    return RELATIVE_URL;
  }

  public User get(UUID id) throws Exception {
    return findOne(User.class, new HashMap<>(Map.of("id", id)));
  }

  public User findOne(String email) throws Exception {
    return findOne(User.class, new HashMap<>(Map.of("email", email)));
  }
  
  public ApiResponse save(UserPostApiRequest request) throws Exception {
    return post(toParams(request));
  }  
  
  public ApiResponse save(UserPutApiRequest request) throws Exception {
    return put(toParams(request));
  } 

  public ApiResponse delete(UserDeleteApiRequest request) throws Exception {
    return delete(toParams(request));
  }
}