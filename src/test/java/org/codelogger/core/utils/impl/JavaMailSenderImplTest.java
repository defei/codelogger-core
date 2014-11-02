package org.codelogger.core.utils.impl;

import static org.junit.Assert.assertTrue;

import org.codelogger.core.bean.MailContent;
import org.codelogger.core.bean.MailParameter;
import org.codelogger.core.utils.MailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class JavaMailSenderImplTest {

  @Autowired
  private MailSender mailSender;

  @Test
  public void sendMail() {

    MailParameter mailParameter =
        new MailParameter("test@ftng.net", "FtngTest", "sexafei@gmail.com", "Test Mail");

    MailContent mailContent = new MailContent() {

      @Override
      public String getContent() {

        return String.format("This email is just for test, current time:%s", new Date());
      }
    };
    boolean sendMail = mailSender.sendMail(mailParameter, mailContent);
    assertTrue(sendMail);
  }
}
