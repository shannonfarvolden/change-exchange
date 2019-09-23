package User;

import io.javalin.http.Handler;

import java.util.Objects;

public class UserController {

    public static Handler fetchAllUsernames = ctx -> {
        UserDao dao = UserDao.instance();
        Iterable<String> allUsers = dao.getAllUsernames();
        ctx.json(allUsers);
    };

    public static Handler fetchById = ctx -> {
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
        User user = findUser(id);
        if (user == null) {
            ctx.html("Not Found");
        } else {
            ctx.json(user);
        }
    };

    public static Handler postTransfer = ctx -> {
        User fromUser = findUser(Integer.parseInt(ctx.formParam("fromUserId")));
        User toUser = findUser(Integer.parseInt(ctx.formParam("toUserId")));
        int amount = Integer.parseInt(ctx.formParam("amount"));
        toUser.transfer(fromUser, amount);

        ctx.html(String.format("Transfer Complete. %s sent %s amount of £%d. %s new balance is %d.", fromUser.getName(), toUser.getName(), amount, toUser.getName(), toUser.getBalance()));
    };

    private static User findUser(int id) throws Exception {
        UserDao dao = UserDao.instance();
        return dao.getUserById(id).orElseThrow(() -> new Exception("Unable to find user id" + id));

    }

}
