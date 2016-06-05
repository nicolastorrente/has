import com.google.inject.Inject;
import controller.RouteController;

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
