package com.norpactech.pf.loader.repository;
/**
 * © 2025 Northern Pacific Technologies, LLC. All Rights Reserved. 
 *  
 * For license details, see the LICENSE file in this project root.
 */
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.norpactech.pf.config.ConfiguredAPI;
import com.norpactech.pf.utils.ApiGetRequest;
import com.norpactech.pf.utils.ApiResponse;
import com.norpactech.pf.utils.TextUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public abstract class ParetoNativeRepository<T> {

  // Configure Gson to use ISO 8601 format for all timestamps
  private static final Gson gson = new GsonBuilder()
      .registerTypeAdapter(Timestamp.class, new JsonSerializer<Timestamp>() {
        @Override
        public JsonElement serialize(Timestamp src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
          return new JsonPrimitive(src.toInstant().toString());
        }
      })
      .create();

  protected abstract String getRelativeURL();

  public T findOne(Class<T> entityType, Map<String, Object> queryParams) throws Exception {
    
    var toStringMap = new HashMap<String, String>();
    for (String key : queryParams.keySet()) {
      Object val = queryParams.get(key);
      if (val != null) {
        toStringMap.put(key, val.toString());
      }
    }
    var request = new ApiGetRequest(entityType, getRelativeURL(), toStringMap);
    var response = get(request);

    if (response.getError() != null) {
      throw new Exception(response.getError().toString());
    }
    if (response.getData() == null) {
      return null;
    }
    return response.readObject(entityType);
  }
  
  public List<T> find(Class<T> entityType, Map<String, Object> queryParams) throws Exception {

    var toStringMap = new HashMap<String, String>();
    for (String key : queryParams.keySet()) {
      Object val = queryParams.get(key);
      if (val != null) {
        toStringMap.put(key, val.toString());
      }
    }
    ApiGetRequest request = new ApiGetRequest(entityType, getRelativeURL(), toStringMap);
    request.setUri(getRelativeURL() + "/find");
    
    ApiResponse response = get(request);

    if (response.getError() != null) {
      throw new Exception(response.getError().toString());
    }
    if (response.getData() == null) {
      return new ArrayList<>();
    }
    return response.readList(entityType);
  }
  
  public T post(Class<T> entityType, Map<String, Object> params) throws Exception {
    

    return null;
  }  
  
  public ApiResponse get(ApiGetRequest apiGetRequest) throws Exception {

    String version = ConfiguredAPI.apiVersion == null ? "" : "/" + ConfiguredAPI.apiVersion;
    String queryString = TextUtils.toQueryString(apiGetRequest.getParams());
    URL url = new URL(ConfiguredAPI.host + version + apiGetRequest.getUri() + queryString.toString());

    okhttp3.Response response = null;
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
        .url(url)
        .get()
        .addHeader("Accept", "application/json")
        .addHeader("Content-Type", "application/json")
        .addHeader("Authorization", "Bearer " + ConfiguredAPI.jwt)
        .addHeader("X-Tenant-ID", System.getenv("PARETO_TENANT_UUID"));
    
    okhttp3.Request request = requestBuilder.build();
    response = client.newCall(request).execute();       

    int responseCode = response.code();
    if (responseCode > 299) {
      throw new Exception("GET Request Failed: " + response.message());
    }
    return gson.fromJson(response.body().string(), ApiResponse.class);
  } 
  
  public ApiResponse post(Map<String, Object> apiPostRequest) throws Exception {

    String version = ConfiguredAPI.apiVersion == null ? "" : "/" + ConfiguredAPI.apiVersion;
    URL url = new URL(ConfiguredAPI.host + version + getRelativeURL());

    OkHttpClient client = new OkHttpClient().newBuilder().build();
    String jsonBody = gson.toJson(apiPostRequest);
    RequestBody requestBody = RequestBody.create(jsonBody, MediaType.get("application/json"));

    okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
        .url(url)
        .post(requestBody)
        .addHeader("Accept", "application/json")
        .addHeader("Content-Type", "application/json")
        .addHeader("Authorization", "Bearer " + ConfiguredAPI.jwt)
        .addHeader("X-Tenant-ID", System.getenv("PARETO_TENANT_UUID"));

    okhttp3.Request request = requestBuilder.build();
    okhttp3.Response response = client.newCall(request).execute();

    int responseCode = response.code();
    if (responseCode > 299) {
        throw new Exception("POST Request Failed: " + response.message());
    }
    return gson.fromJson(response.body().string(), ApiResponse.class);
  }
  
  public ApiResponse put(Map<String, Object> apiPutRequest) throws Exception {

    String version = ConfiguredAPI.apiVersion == null ? "" : "/" + ConfiguredAPI.apiVersion;
    URL url = new URL(ConfiguredAPI.host + version + getRelativeURL());

    OkHttpClient client = new OkHttpClient().newBuilder().build();
    String jsonBody = gson.toJson(apiPutRequest);
    RequestBody requestBody = RequestBody.create(jsonBody, MediaType.get("application/json"));

    okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
        .url(url)
        .put(requestBody)
        .addHeader("Accept", "application/json")
        .addHeader("Content-Type", "application/json")
        .addHeader("Authorization", "Bearer " + ConfiguredAPI.jwt)
        .addHeader("X-Tenant-ID", System.getenv("PARETO_TENANT_UUID"));

    okhttp3.Request request = requestBuilder.build();
    okhttp3.Response response = client.newCall(request).execute();

    int responseCode = response.code();
    if (responseCode > 299) {
        throw new Exception("POST Request Failed: " + response.message());
    }
    return gson.fromJson(response.body().string(), ApiResponse.class);
  }
  
  public ApiResponse delete(Map<String, Object> apiDeleteRequest) throws Exception {

    String version = ConfiguredAPI.apiVersion == null ? "" : "/" + ConfiguredAPI.apiVersion;
    URL url = new URL(ConfiguredAPI.host + version + getRelativeURL());

    OkHttpClient client = new OkHttpClient().newBuilder().build();
    String jsonBody = gson.toJson(apiDeleteRequest);
    RequestBody requestBody = RequestBody.create(jsonBody, MediaType.get("application/json"));

    okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
        .url(url)
        .delete(requestBody)
        .addHeader("Accept", "application/json")
        .addHeader("Content-Type", "application/json")
        .addHeader("Authorization", "Bearer " + ConfiguredAPI.jwt)
        .addHeader("X-Tenant-ID", System.getenv("PARETO_TENANT_UUID"));

    okhttp3.Request request = requestBuilder.build();
    okhttp3.Response response = client.newCall(request).execute();

    int responseCode = response.code();
    if (responseCode > 299) {
        throw new Exception("POST Request Failed: " + response.message());
    }
    return gson.fromJson(response.body().string(), ApiResponse.class);
  }
  
  public Map<String, Object> toParams(Object request) throws IllegalAccessException {
    
    Map<String, Object> map = new LinkedHashMap<>();
    Class<?> clazz = request.getClass();

    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      map.put(field.getName(), field.get(request));
    }
    return map;
  }
}