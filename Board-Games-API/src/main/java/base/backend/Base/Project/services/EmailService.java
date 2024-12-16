package base.backend.Base.Project.services;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final Session mailSession;

  @Autowired
  public EmailService(Session mailSession) {
    this.mailSession = mailSession;
  }

  public void sendEmail(String email, String subject, String text) {
    try {
      Message message = new MimeMessage(mailSession);
      message.setFrom(new InternetAddress("lc.devsparkclub@yandex.by"));
      message.setRecipient(
        Message.RecipientType.TO,
        new InternetAddress(email)
      );
      message.setSubject(subject);
      message.setText(text);
      Transport.send(message);
    } catch (Exception e) {
      System.err.println("Failed to send email to: " + email);
    }
  }
}
