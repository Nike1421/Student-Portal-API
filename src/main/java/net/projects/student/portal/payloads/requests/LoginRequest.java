package net.projects.student.portal.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  @NotBlank
  private String sapID;

  @NotBlank
  private String password;
}
