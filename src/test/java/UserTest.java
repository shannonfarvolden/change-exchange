import User.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    User user1;
    User user2;

    @Before
    public void setup(){
        user1 = new User(0,"Billy Bob", 10);
        user2 = new User(0,"Jane Doe", 10);

    }

    @Test
    public void tranferMoneyAdjustsBalance() throws Exception{
        user1.transfer(user2, 5);

        assertEquals(15, user1.getBalance());
        assertEquals(5, user2.getBalance());

    }

    @Test(expected = Exception.class)
    public void exceptionThrownWhenUserHasInsufficientFunds() throws Exception{
        user1.transfer(user2, 15);
    }


    @After
    public void tearDown(){
        user1 = null;
        user2 = null;
    }
}
