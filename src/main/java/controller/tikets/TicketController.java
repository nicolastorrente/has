package controller.tikets;

import api.ticket.Ticket;
import com.google.inject.Inject;
import org.apache.log4j.Logger;

public class TicketController {

    private final MailSender mailSender;

    final private static Logger logger = Logger.getLogger(TicketController.class);

    @Inject
    public TicketController(MailCredentialsContainer mailCredentialsContainer, MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void addTicket(Ticket ticket) {
        logger.info("Enviando mail de ticket...");
        this.mailSender.sendSimpleMail(ticket);
        logger.info("Mail de Ticket enviado correctamente.");
    }

}
