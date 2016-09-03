package controller.tikets;

import api.ticket.Photo;
import api.ticket.Ticket;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.net.MalformedURLException;
import java.net.URL;
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

            if (ticket.getPhotos() != null && !ticket.getPhotos().isEmpty()) {
                MimeMultipart multipart = new MimeMultipart("related");
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(this.generateBody(ticket), "text/html");
                multipart.addBodyPart(messageBodyPart);
                for(Photo photo : ticket.getPhotos()) {
                    if (photo.getFileId() != null && photo.getFilePath() != null) {
                        messageBodyPart = new MimeBodyPart();
                        URL url = new URL(this.generatePhotoUrl(photo));
//                        URL url = new URL("http://ramazancalay.com/qygxsyu7/pic-2.bp.blogspot.com/_Yb58DyFPuKs/TRwRQ0EQOPI/AAAAAAAAABk/QXoOftzO0g4/s1600/600px-Example.svg.png");
                        URLDataSource ds = new URLDataSource(url);
                        messageBodyPart.setDataHandler(new DataHandler(ds));
                        messageBodyPart.setHeader("Content-ID", "<image>");
                        multipart.addBodyPart(messageBodyPart);
                    }
                }
                message.setContent(multipart);
            } else {
                message.setContent(this.generateBody(ticket), "text/html");
            }

            Transport.send(message);

        } catch (MessagingException e) {
            logger.error("Error mandando mail de ticket: ", e);
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            logger.error("URL malformada en la creacion de ticket: ", e);
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

    private String generatePhotoUrl(Photo photo) {
        String url = "https://api.telegram.org/file/bot219665776:";
        url = url.concat(photo.getFileId());
        url = url.concat("/");
        url = url.concat(photo.getFilePath());
        return url;
    }

    private String generateBody(Ticket ticket) {
        String htmlText = "";
        htmlText = htmlText.concat("Ubicacion: ").concat(ticket.getLocation()).concat("<br>");
        htmlText = htmlText.concat("Descripción: ").concat(ticket.getDescription()).concat("<br>");
        htmlText = htmlText.concat("Legajo de usuario: ").concat(ticket.getUser()).concat("<br><br>");
        htmlText = htmlText.concat("--<br> Hali :)");
        return htmlText;
    }
}
