package User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDao {

    private List<User> users = Arrays.asList(
            new User(0, "Michael Scott", 500),
            new User(1, "Dwight Schrute", 10000),
            new User(2, "Jim Halpert", 300),
            new User(3, "Pam Beesly", 150)

    );

    private static UserDao userDao = null;

    private UserDao() {
    }

    static UserDao instance() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    Iterable<String> getAllUsernames() {
        return users.stream()
                .map(user -> user.getName())
                .collect(Collectors.toList());
    }

    Optional<User> getUserById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findAny();
    }

}
