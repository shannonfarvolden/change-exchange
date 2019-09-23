package User;

import io.javalin.http.Handler;

import java.util.Objects;

public class UserController {

    public static UserDao dao = UserDao.instance();

    public static Handler getUsernames = ctx -> {
        Iterable<String> allUsers = dao.getUsernames();
        ctx.json(allUsers);
    };

    public static Handler getUser = ctx -> {
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
        User user = dao.getUser(id).orElseThrow(() -> new Exception("Unable to find user id" + id));
        if (user == null) {
            ctx.html("Not Found");
        } else {
            ctx.json(user);
        }
    };

    public static Handler postTransfer = ctx -> {
        int fromUserId = Integer.parseInt(ctx.formParam("fromUserId"));
        int toUserId = (Integer.parseInt(ctx.formParam("toUserId")));

        User fromUser = dao.getUser(fromUserId).orElseThrow(() -> new Exception("Unable to find user id" + fromUserId));
        User toUser = dao.getUser(toUserId).orElseThrow(() -> new Exception("Unable to find user id" + toUserId));
        int amount = Integer.parseInt(ctx.formParam("amount"));
        toUser.transfer(fromUser, amount);

        ctx.html(String.format("Transfer Complete. %s sent %s amount of £%d. %s new balance is £%d.", fromUser.getName(), toUser.getName(), amount, toUser.getName(), toUser.getBalance()));
    };

}
