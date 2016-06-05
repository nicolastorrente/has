package controller;

import api.Ticket;
import org.apache.log4j.Logger;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public class TicketController {

    final private static Logger logger = Logger.getLogger(TicketController.class);

    public void addTicket(Ticket ticket) {
        logger.info(ticket.getDescription());
    }

}
