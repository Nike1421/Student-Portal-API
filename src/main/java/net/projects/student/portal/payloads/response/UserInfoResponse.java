package net.projects.student.portal.payloads.response;

import java.util.List;

public class UserInfoResponse {
	  private String id;
	  private String sapId;
	  private String email;
	  private List<String> roles;

	  public UserInfoResponse(String id, String sapId, String email, List<String> roles) {
	    this.id = id;
	    this.sapId = sapId;
	    this.email = email;
	    this.roles = roles;
	  }

	  public String getId() {
	    return id;
	  }

	  public void setId(String id) {
	    this.id = id;
	  }

	  public String getEmail() {
	    return email;
	  }

	  public void setEmail(String email) {
	    this.email = email;
	  }

	  public String getSapId() {
	    return sapId;
	  }

	  public void setSapId(String sapId) {
	    this.sapId = sapId;
	  }

	  public List<String> getRoles() {
	    return roles;
	  }
	}

