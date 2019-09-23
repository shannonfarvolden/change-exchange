import User.UserController;
import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.result("Welcome to Change Exchange"));
        app.get("/users", UserController.fetchAllUsernames);
        app.get("/users/:id", UserController.fetchById);
    }
}