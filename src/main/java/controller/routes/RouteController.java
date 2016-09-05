package controller.routes;

import api.SimpleResponse;
import api.Wifi;
import api.ticket.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.inject.Inject;
import controller.tikets.TicketController;
import org.apache.log4j.Logger;

import static spark.Spark.*;

public class RouteController {

    private final ObjectMapper objectMapper;
    private final TicketController ticketController;

    final private static Logger logger = Logger.getLogger(RouteController.class);

    @Inject
    public RouteController(TicketController ticketController, ObjectMapper objectMapper) {
        this.ticketController = ticketController;
        this.objectMapper = objectMapper;
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

    public void register() {
        port(Integer.valueOf(System.getenv("PORT") != null ? Integer.valueOf(System.getenv("PORT")) : 8087));

        get("/has/wifi", (request, response) -> {
            response.header("Content-Type", "application/json");
            return objectMapper.writeValueAsString(new Wifi());
        });

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

