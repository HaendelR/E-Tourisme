package first.app.e_tourisme.controller;

import first.app.e_tourisme.model.User;

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
    public Boolean verifyLogin(String username, String password) {
        User user = new User();
        return user.login(username, password);
    }

}
