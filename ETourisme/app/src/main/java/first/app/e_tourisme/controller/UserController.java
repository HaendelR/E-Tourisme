package first.app.e_tourisme.controller;

import android.content.Context;

import first.app.e_tourisme.model.User;
import first.app.e_tourisme.tools.ResponseCallBack;
import first.app.e_tourisme.tools.SignInCallBack;

// Final because we don't want another class to derive from it and create another instance
public final class UserController {
    private static UserController instance = null;

    private UserController() {
        super();
    }

    /**
     * Create instance
     *
     * @return instance
     */
    public static final UserController getInstance() {
        if (UserController.instance == null) {
            UserController.instance = new UserController();
        }
        return UserController.instance;
    }

    /**
     * Verify authentication
     *
     * @param username
     * @param password
     * @return true or false
     */
    public void verifyLogin(String username, String password, Context context, ResponseCallBack callBack) {
        User user = new User();
        user.login(username, password, context, callBack);
    }

    public void signInUser(User user, SignInCallBack callBack) {
        user.signIn(user, callBack);
    }

}
