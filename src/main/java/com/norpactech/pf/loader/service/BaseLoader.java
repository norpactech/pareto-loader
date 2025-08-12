package com.norpactech.pf.loader.service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.norpactech.pf.loader.repository.ContextDataTypeRepository;
import com.norpactech.pf.loader.repository.ContextPropertyTypeRepository;
import com.norpactech.pf.loader.repository.ContextRepository;
import com.norpactech.pf.loader.repository.GenericDataTypeAttributeRepository;
import com.norpactech.pf.loader.repository.GenericDataTypeRepository;
import com.norpactech.pf.loader.repository.GenericPropertyTypeRepository;
import com.norpactech.pf.loader.repository.PluginRepository;
import com.norpactech.pf.loader.repository.ProjectComponentPropertyRepository;
import com.norpactech.pf.loader.repository.ProjectComponentRepository;
import com.norpactech.pf.loader.repository.ProjectRepository;
import com.norpactech.pf.loader.repository.RefTableTypeRepository;
import com.norpactech.pf.loader.repository.RefTablesRepository;
import com.norpactech.pf.loader.repository.SchemaRepository;
import com.norpactech.pf.loader.repository.TenantRepository;
import com.norpactech.pf.loader.repository.TenantUserRepository;
import com.norpactech.pf.loader.repository.UserRepository;
import com.norpactech.pf.loader.repository.ValidationRepository;

public abstract class BaseLoader {

  protected static final ContextRepository contextRepository = new ContextRepository();
  protected static final ContextDataTypeRepository contextDataTypeRepository = new ContextDataTypeRepository();
  protected static final ContextPropertyTypeRepository contextPropertyTypeRepository = new ContextPropertyTypeRepository();
  protected static final GenericDataTypeAttributeRepository genericDataTypeAttributeRepository = new GenericDataTypeAttributeRepository();
  protected static final GenericDataTypeRepository genericDataTypeRepository = new GenericDataTypeRepository();
  protected static final GenericPropertyTypeRepository genericPropertyTypeRepository = new GenericPropertyTypeRepository();
  protected static final PluginRepository pluginRepository = new PluginRepository();
  protected static final ProjectComponentPropertyRepository projectComponentPropertyRepository = new ProjectComponentPropertyRepository();
  protected static final ProjectComponentRepository projectComponentRepository = new ProjectComponentRepository();
  protected static final ProjectRepository projectRepository = new ProjectRepository();
  protected static final RefTablesRepository refTablesRepository = new RefTablesRepository();
  protected static final RefTableTypeRepository refTableTypeRepository = new RefTableTypeRepository();
  protected static final SchemaRepository schemaRepository = new SchemaRepository();
  protected static final TenantRepository tenantRepository = new TenantRepository();
  protected static final TenantUserRepository tenantUserRepository = new TenantUserRepository();
  protected static final UserRepository userRepository = new UserRepository();
  protected static final ValidationRepository validationRepository = new ValidationRepository();

  private String filePath;
  private String fileName;
  private CSVParser csvParser;
  
  public BaseLoader (String filePath, String fileName) throws Exception {
    this.filePath = filePath;
    this.fileName = fileName;
    
    Reader reader = Files.newBufferedReader(getFullPath());
    this.csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build());
  }
  
  public boolean isComment(CSVRecord csvRecord) throws Exception {

    String start = null;
    try {
      start = csvRecord.get(0);

      if(!StringUtils.isNotEmpty(start) ) {
        throw new Exception("Record invalid - col(0) is empty");
      }
      return start.startsWith("//");
    }
    catch (Exception e) {
      throw new Exception("Record col(0) is empty or currupt");
    }
  }
  
  public Path getFullPath() {
    return Paths.get(filePath, fileName);
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public CSVParser getCsvParser() {
    return csvParser;
  }

  public void setCsvParser(CSVParser csvParser) {
    this.csvParser = csvParser;
  }
}  