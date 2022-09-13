package com.gupiao.bean;

import lombok.Data;

@Data
public class StockPlatform {

  private String platName;
  private String platCode;

  public String getPlatName() {
    return platName;
  }

  public void setPlatName(String platName) {
    this.platName = platName;
  }

  public String getPlatCode() {
    return platCode;
  }

  public void setPlatCode(String platCode) {
    this.platCode = platCode;
  }

}
