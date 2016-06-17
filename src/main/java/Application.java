import com.google.inject.Inject;
import controller.routes.RouteController;

public class Application {

    RouteController routeController;

    @Inject
    public Application(RouteController routeController) {
        this.routeController = routeController;
    }

    public void run() {
        routeController.register();
    }
}
