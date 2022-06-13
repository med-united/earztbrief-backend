package health.medunited.config;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class MailConfig {

    @ConfigProperty(name = "mail.smtp.host")
    String smtpHost;

    @ConfigProperty(name = "mail.smtp.user")
    String smtpUser;

    @ConfigProperty(name = "mail.smtp.password")
    String smtpPassword;

    public String getSmtpHost() {
        return smtpHost;
    }

    public String getSmtpUser() {
        return smtpUser;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

}
