package health.medunited.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "mail.smtp")
public interface MailConfiguration {
    String host();
    String user();
    String password();
}
