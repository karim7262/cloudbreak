package com.sequenceiq.mock.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * This API class represents a request to retrieve Auto-TLS certificates for a given host. The request contains the host requesting the certificate, a valid token generated by the certmanager utility, and optionally a certificate signing request.  Currently, the certificate signing request argument is not supported.
 */
@ApiModel(description = "This API class represents a request to retrieve Auto-TLS certificates for a given host. The request contains the host requesting the certificate, a valid token generated by the certmanager utility, and optionally a certificate signing request.  Currently, the certificate signing request argument is not supported.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-10T21:24:30.629+01:00")




public class ApiCertificateRequest   {
  @JsonProperty("hostname")
  private String hostname = null;

  @JsonProperty("csr")
  private String csr = null;

  @JsonProperty("token")
  private String token = null;

  @JsonProperty("subjectAltNames")
  @Valid
  private List<String> subjectAltNames = null;

  public ApiCertificateRequest hostname(String hostname) {
    this.hostname = hostname;
    return this;
  }

  /**
   * Get the hostname of the host requesting certificates
   * @return hostname
  **/
  @ApiModelProperty(value = "Get the hostname of the host requesting certificates")


  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public ApiCertificateRequest csr(String csr) {
    this.csr = csr;
    return this;
  }

  /**
   * Get the certificate signing request in PEM format
   * @return csr
  **/
  @ApiModelProperty(value = "Get the certificate signing request in PEM format")


  public String getCsr() {
    return csr;
  }

  public void setCsr(String csr) {
    this.csr = csr;
  }

  public ApiCertificateRequest token(String token) {
    this.token = token;
    return this;
  }

  /**
   * Get the certificate request token
   * @return token
  **/
  @ApiModelProperty(value = "Get the certificate request token")


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public ApiCertificateRequest subjectAltNames(List<String> subjectAltNames) {
    this.subjectAltNames = subjectAltNames;
    return this;
  }

  public ApiCertificateRequest addSubjectAltNamesItem(String subjectAltNamesItem) {
    if (this.subjectAltNames == null) {
      this.subjectAltNames = new ArrayList<>();
    }
    this.subjectAltNames.add(subjectAltNamesItem);
    return this;
  }

  /**
   * 
   * @return subjectAltNames
  **/
  @ApiModelProperty(value = "")


  public List<String> getSubjectAltNames() {
    return subjectAltNames;
  }

  public void setSubjectAltNames(List<String> subjectAltNames) {
    this.subjectAltNames = subjectAltNames;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiCertificateRequest apiCertificateRequest = (ApiCertificateRequest) o;
    return Objects.equals(this.hostname, apiCertificateRequest.hostname) &&
        Objects.equals(this.csr, apiCertificateRequest.csr) &&
        Objects.equals(this.token, apiCertificateRequest.token) &&
        Objects.equals(this.subjectAltNames, apiCertificateRequest.subjectAltNames);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hostname, csr, token, subjectAltNames);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiCertificateRequest {\n");
    
    sb.append("    hostname: ").append(toIndentedString(hostname)).append("\n");
    sb.append("    csr: ").append(toIndentedString(csr)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    subjectAltNames: ").append(toIndentedString(subjectAltNames)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

