package com.norpactech.pf.loader.service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.norpactech.pf.loader.repository.SchemaRepository;
import com.norpactech.pf.loader.repository.TenantRepository;
import com.norpactech.pf.loader.repository.UserRepository;

public abstract class BaseLoader {

  protected static final TenantRepository tenantRepository = new TenantRepository();
  protected static final SchemaRepository schemaRepository = new SchemaRepository();
  protected static final UserRepository userRepository = new UserRepository();

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