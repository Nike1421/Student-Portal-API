package net.projects.student.portal.payloads.requests;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
  @NotBlank
  private String sapId;

  @NotBlank
  private String password;

  public String getSapId() {
    return sapId;
  }

  public void setsapId(String sapId) {
    this.sapId = sapId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
