package base.backend.Base.Project.config;

import jakarta.mail.*;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

  private final String username = "dont-hurt-me-no-more@yandex.ru";

  private final String password = "vlpkmbmfghwfcuds";

  @Bean
  public Session mailSession() {
    Properties properties = new Properties();
    String host = "smtp.yandex.ru";
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.auth", "true");
    int port = 465;
    properties.put("mail.smtp.socketFactory.port", port);
    properties.put(
      "mail.smtp.socketFactory.class",
      "javax.net.ssl.SSLSocketFactory"
    );

    return Session.getDefaultInstance(
      properties,
      new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      }
    );
  }
}
