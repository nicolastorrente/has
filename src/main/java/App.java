import com.google.inject.Guice;

public class App {
    public static void main(String[] args) {
        Application application = Guice.createInjector().getInstance(Application.class);
        application.run();
    }
}
