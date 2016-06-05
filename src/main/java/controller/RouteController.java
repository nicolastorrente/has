package controller;

import api.SimpleResponse;
import api.Ticket;
import api.Wifi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.apache.log4j.Logger;

import static spark.Spark.get;
import static spark.Spark.post;

public class RouteController {

    private final ObjectMapper objectMapper;
    private final TicketController ticketController;

    final private static Logger logger = Logger.getLogger(RouteController.class);

    @Inject
    public RouteController(TicketController ticketController, ObjectMapper objectMapper) {
        this.ticketController = ticketController;
        this.objectMapper = objectMapper;
    }

    public void register() {
        get("/has/wifi", (request, response) -> objectMapper.writeValueAsString(new Wifi()));

        post("/has/tickets", (request, response) -> {
            try {
                Ticket ticket = objectMapper.readValue(request.body(), Ticket.class);
                this.ticketController.addTicket(ticket);
                response.status(201);
                response.header("Content-Type", "application/json");
                return objectMapper.writeValueAsString(new SimpleResponse(SimpleResponse.Status.OK));
            } catch (Exception e) {
                logger.error("error: ", e);
                response.status(500);
                response.header("Content-Type", "application/json");
                return objectMapper.writeValueAsString(new SimpleResponse(SimpleResponse.Status.ERROR, e.getMessage()));
            }
        });
    }
}

