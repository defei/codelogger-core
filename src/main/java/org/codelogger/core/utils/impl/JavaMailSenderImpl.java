package org.codelogger.core.utils.impl;

import org.codelogger.core.bean.MailContent;
import org.codelogger.core.bean.MailParameter;
import org.codelogger.core.utils.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailSenderImpl implements MailSender {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private JavaMailSender mailSender;

  @Override
  public boolean sendMail(final MailParameter mailParameter, final MailContent mailContent) {

    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      Address address = new InternetAddress(mailParameter.getFrom(), mailParameter.getFromName());
      mimeMessage.setFrom(address);
      mimeMessage.setSubject(mailParameter.getSubject());
      mimeMessage.setText(mailContent.getContent());
      mimeMessage.setSentDate(new Date());
      mimeMessage
          .setRecipient(Message.RecipientType.TO, new InternetAddress(mailParameter.getTo()));
      mailSender.send(mimeMessage);
      log.info("Mail successful sent to email {}.", mailParameter.getTo());
    } catch (Exception e) {
      log.warn("Get an exception when send email.", e);
      return false;
    }
    return true;
  }

  public JavaMailSender getMailSender() {

    return mailSender;
  }

  public void setMailSender(final JavaMailSender mailSender) {

    this.mailSender = mailSender;
  }

}
