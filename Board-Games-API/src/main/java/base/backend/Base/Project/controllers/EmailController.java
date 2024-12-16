package base.backend.Base.Project.controllers;

import base.backend.Base.Project.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
@Tag(name = "Emails", description = "Operations for interacting with emails")
public class EmailController {

  private final EmailService emailService;

  public EmailController(EmailService emailService) {
    this.emailService = emailService;
  }

  @Operation(summary = "Send email")
  @PostMapping
  public ResponseEntity<Void> sendEmail(
    @RequestParam String email,
    @RequestParam String subject,
    @RequestParam String text
  ) {
    emailService.sendEmail(email, subject, text);
    return ResponseEntity.noContent().build();
  }
}
