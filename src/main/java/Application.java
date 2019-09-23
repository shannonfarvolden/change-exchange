import User.UserController;
import io.javalin.Javalin;

public class Application {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        routeSetup(app);
    }

    public static void routeSetup(Javalin app){

        app.get("/", ctx -> ctx.html("Welcome to Change Exchange"));
        app.post("/users/transfer", UserController.postTransfer);
        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(404);
        }).error(404, ctx -> {
            ctx.result("Page not found.");
        });
    }

}