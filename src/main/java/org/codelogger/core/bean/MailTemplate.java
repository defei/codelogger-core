package org.codelogger.core.bean;

public class MailTemplate {

  private final MailParameter mailParameter;

  private final MailContent mailContent;

  private MailTemplate(final MailParameter mailParameter, final MailContent mailContent) {

    this.mailParameter = mailParameter;
    this.mailContent = mailContent;
  }

  public String getMailContent() {

    return null;
  }

}
