package com.example.skillbridge.services;

import com.example.skillbridge.models.BlogPost;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final String adminEmail = "omarmansour085@gmail.com";

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendNewPostNotification(BlogPost post) {
        try {
            Context context = new Context();
            context.setVariable("title", post.getTitle());
            context.setVariable("author", post.getAuthor().getUsername());
            context.setVariable("createdAt", post.getCreatedAt());
            context.setVariable("link", "https://yourdomain.com/posts/" + post.getId());

            String content = templateEngine.process("email/new-post", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(adminEmail);
            helper.setSubject("New Post Created: " + post.getTitle());
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception
            System.err.println("Email sending failed: " + e.getMessage());
        }
    }
}