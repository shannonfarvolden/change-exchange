package User;

import io.javalin.http.Handler;

import java.util.Objects;

public class UserController {

    public static UserDao dao = UserDao.instance();

    public static Handler postTransfer = ctx -> {
        int fromUserId = Integer.parseInt(Objects.requireNonNull(ctx.formParam("fromUserId")));
        int toUserId = Integer.parseInt(Objects.requireNonNull(ctx.formParam("toUserId")));

        User fromUser = dao.getUser(fromUserId).orElseThrow(() -> new Exception("Unable to find user id" + fromUserId));
        User toUser = dao.getUser(toUserId).orElseThrow(() -> new Exception("Unable to find user id" + toUserId));
        int amount = Integer.parseInt(Objects.requireNonNull(ctx.formParam("amount")));
        toUser.transfer(fromUser, amount);

        ctx.html(String.format("Transfer Complete. %s sent %s amount of %d. %s new balance is %d.", fromUser.getName(), toUser.getName(), amount, toUser.getName(), toUser.getBalance()));
    };

}
