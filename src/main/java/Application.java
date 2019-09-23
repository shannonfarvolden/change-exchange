import User.UserController;
import io.javalin.Javalin;

public class Application {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> ctx.html("Welcome to Change Exchange"));
        app.get("/users", UserController.getUsernames);
        app.get("/users/:id", UserController.getUser);
        app.post("/users/transfer", UserController.postTransfer);
    }
}