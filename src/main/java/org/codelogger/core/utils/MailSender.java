package org.codelogger.core.utils;

import org.codelogger.core.bean.MailContent;
import org.codelogger.core.bean.MailParameter;

public interface MailSender {

  public boolean sendMail(MailParameter mailParameter, MailContent mailContent);
}
