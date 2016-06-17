package controller.tikets;

import api.Ticket;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    private final Session session;
    private final MailCredentialsContainer mailCredentialsContainer;

    final private static Logger logger = Logger.getLogger(MailSender.class);

    @Inject
    public MailSender(MailCredentialsContainer mailCredentialsContainer) {
        this.mailCredentialsContainer = mailCredentialsContainer;
        Properties properties = this.generateProperties();
        MailCredentials mailCredentials = mailCredentialsContainer.getMailCredentials();
        this.session = this.generateSession(properties, mailCredentials);
    }

    public void sendSimpleMail(Ticket ticket) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailCredentialsContainer.getMailCredentials().getMail()));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailCredentialsContainer.getMailCredentials().getMail()));
            message.setSubject(ticket.getSubject());
            message.setText(ticket.getDescription()
                    + "\n\nUbicacion: " + ticket.getLocation());

            Transport.send(message);

        } catch (MessagingException e) {
            logger.error("Error mandando mail de ticket: ", e);
            throw new RuntimeException(e);
        }
    }

    private Properties generateProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    private Session generateSession(Properties properties, MailCredentials mailCredentials) {
        return Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailCredentials.getMail(), mailCredentials.getPassword());
                    }
                });
    }
}
