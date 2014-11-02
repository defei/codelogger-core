package org.codelogger.core.bean;

import java.util.List;

public class MailParameter {

  private String from;

  private String fromName;

  private String to;

  private String subject;

  private List<String> cc;

  private List<String> bc;

  private String apiUser;

  private String apiKey;

  public MailParameter() {

  }

  public MailParameter(final String from, final String fromName, final String to,
      final String subject) {

    this.from = from;
    this.fromName = fromName;
    this.to = to;
    this.subject = subject;
  }

  public void setApiUserAndKey(final String apiUser, final String apiKey) {

    this.apiUser = apiUser;
    this.apiKey = apiKey;
  }

  public String getFrom() {

    return from;
  }

  public void setFrom(final String from) {

    this.from = from;
  }

  public String getFromName() {

    return fromName;
  }

  public void setFromName(final String fromName) {

    this.fromName = fromName;
  }

  public String getTo() {

    return to;
  }

  public void setTo(final String to) {

    this.to = to;
  }

  public String getSubject() {

    return subject;
  }

  public void setSubject(final String subject) {

    this.subject = subject;
  }

  public List<String> getCc() {

    return cc;
  }

  public void setCc(final List<String> cc) {

    this.cc = cc;
  }

  public List<String> getBc() {

    return bc;
  }

  public void setBc(final List<String> bc) {

    this.bc = bc;
  }

  public String getApiUser() {

    return apiUser;
  }

  public String getApiKey() {

    return apiKey;
  }

}
