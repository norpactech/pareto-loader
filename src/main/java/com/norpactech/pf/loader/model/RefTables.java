package com.norpactech.pf.loader.model;
/**
 * © 2025 Northern Pacific Technologies, LLC. All Rights Reserved. 
 *  
 * For license details, see the LICENSE file in this project root.
 */
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

/**
 * API Model Class: RefTables - Reference Tables
 */
public class RefTables extends BaseModel {

  private UUID id;
  private UUID idRefTableType;
  private String refTableTypeName;
  private String name;
  private String description;
  private String value;
  private Integer sequence;
  private Timestamp createdAt;
  private String createdBy;
  private Timestamp updatedAt;
  private String updatedBy;
  private Boolean isActive;

  public RefTables () {}
  public RefTables (Object obj) {
    super(obj);
  }

  public RefTables (
    UUID id,
    UUID idRefTableType,
    String refTableTypeName,
    String name,
    String description,
    String value,
    Integer sequence,
    Timestamp createdAt,
    String createdBy,
    Timestamp updatedAt,
    String updatedBy,
    Boolean isActive)
 {
    this.id = id;
    this.idRefTableType = idRefTableType;
    this.refTableTypeName = refTableTypeName;
    this.name = name;
    this.description = description;
    this.value = value;
    this.sequence = sequence;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.updatedAt = updatedAt;
    this.updatedBy = updatedBy;
    this.isActive = isActive;
  }

  public static Map<String, Object> queryRequest(Map<String, String> queryParams) throws Exception {
    
    var matchedParams = paramMatcher(queryParams, RefTables.class);
    matchedParams.put("sql", 
      "SELECT pareto.ref_tables.*, " + 
      "pareto.ref_table_type.name as ref_table_type_name " + 
      "FROM pareto.ref_tables " + 
      "JOIN pareto.ref_table_type on (pareto.ref_table_type.id = pareto.ref_tables.id_ref_table_type)");
    return matchedParams;
  }

  // Getters and Setters...
    
  public UUID getId() {
    return this.id;
  }
    
  public UUID setId(UUID id) {
    return this.id = id;
  }    
    
  public UUID getIdRefTableType() {
    return this.idRefTableType;
  }
    
  public UUID setIdRefTableType(UUID idRefTableType) {
    return this.idRefTableType = idRefTableType;
  }    
    
  public String getRefTableTypeName() {
    return this.refTableTypeName;
  }
    
  public String setRefTableTypeName(String refTableTypeName) {
    return this.refTableTypeName = refTableTypeName;
  }    
    
  public String getName() {
    return this.name;
  }
    
  public String setName(String name) {
    return this.name = name;
  }    
    
  public String getDescription() {
    return this.description;
  }
    
  public String setDescription(String description) {
    return this.description = description;
  }    
    
  public String getValue() {
    return this.value;
  }
    
  public String setValue(String value) {
    return this.value = value;
  }    
    
  public Integer getSequence() {
    return this.sequence;
  }
    
  public Integer setSequence(Integer sequence) {
    return this.sequence = sequence;
  }    
    
  public Timestamp getCreatedAt() {
    return this.createdAt;
  }
    
  public Timestamp setCreatedAt(Timestamp createdAt) {
    return this.createdAt = createdAt;
  }    
    
  public String getCreatedBy() {
    return this.createdBy;
  }
    
  public String setCreatedBy(String createdBy) {
    return this.createdBy = createdBy;
  }    
    
  public Timestamp getUpdatedAt() {
    return this.updatedAt;
  }
    
  public Timestamp setUpdatedAt(Timestamp updatedAt) {
    return this.updatedAt = updatedAt;
  }    
    
  public String getUpdatedBy() {
    return this.updatedBy;
  }
    
  public String setUpdatedBy(String updatedBy) {
    return this.updatedBy = updatedBy;
  }    
    
  public Boolean getIsActive() {
    return this.isActive;
  }
    
  public Boolean setIsActive(Boolean isActive) {
    return this.isActive = isActive;
  }    
}