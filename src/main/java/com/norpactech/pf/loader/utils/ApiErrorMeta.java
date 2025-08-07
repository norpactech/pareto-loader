package com.norpactech.pf.loader.utils;
import com.norpactech.pf.loader.enums.EnumApiCodes;

public class ApiErrorMeta extends Meta {
  
  public ApiErrorMeta(EnumApiCodes apiCode, String errorCode, String message, String hint, String detail) {
    super(apiCode, errorCode, message, hint, detail);
  }
}

