package com.radoslawzerek.bigbetbackend.service;

import com.radoslawzerek.bigbetbackend.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    private final AdminConfig adminConfig;

    @Qualifier("templateEngine")
    private final TemplateEngine templateEngine;


    @Autowired
    public MailCreatorService(AdminConfig adminConfig, TemplateEngine templateEngine) {
        this.adminConfig = adminConfig;
        this.templateEngine = templateEngine;
    }

    public String buildBetsWonInfoEmail(String message, String username) {

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("username", username);
        context.setVariable("goodbye_message", "Best Regards,");
        context.setVariable("admin_config", adminConfig);
        return templateEngine.process("mail/bets-won-info-mail", context);
    }
}
