package first.app.e_tourisme.model;

import junit.framework.TestCase;

import java.util.Date;

public class UserTest extends TestCase {
    // create user
    private User user = new User("user", "default", "admin", 1, "address", "default@gmail.com", 0315, new Date());
    private Boolean isLogin = true;

    public void testLogin() {
      //  assertEquals(isLogin, user.login("admin", "admin"));
    }
}