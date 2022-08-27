package tugasakhir.pemesanan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


public class EmailConfig {

    @Value("@{spring.mail.host}")
    String mailHost;

    @Value(("@{spring.mail.port}"))
    Integer mailPort;

    @Value(("@{spring.mail.username}"))
    String mailUsername;

    @Value(("@{spring.mail.password}"))
    String mailPassword;

    @Value("@{spring.mail.properties.mail.smtp.auth}")
    String mailSetAuth;

    @Value("@{spring.mail.properties.mail.smtp.starttls.enable}")
    String mailStartTls;


    @Bean
    public JavaMailSenderImpl getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);

        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", mailSetAuth);
        props.put("mail.smtp.starttls.enable",mailStartTls );
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return mailSender;
    }

}
