package base.backend.Base.Project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .components(new Components())
      .info(
        new Info()
          .title("Yet Another API")
          .description(
            "This API serves the Yet Another System, allowing interactions with various endpoints."
          )
          .version("0.0.1")
          .contact(
            new Contact()
              .name("Vladislav Torchilo")
              .email("fcad.td@gmail.com")
              .url("")
          )
      );
  }
}
