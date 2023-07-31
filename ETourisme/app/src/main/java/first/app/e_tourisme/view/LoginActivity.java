package first.app.e_tourisme.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import first.app.e_tourisme.R;
import first.app.e_tourisme.controller.UserController;

public class LoginActivity extends AppCompatActivity {

    // Propriety login
    private EditText txtUsername;
    private EditText txtPassword;

    private TextView errorLogin;
    private UserController userController;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        initLogin();
        this.userController = UserController.getInstance();
    }

    /**
     * Initialize with view object
     */
    private void initLogin() {
        txtUsername = (EditText) findViewById(R.id.usernameLogin);
        txtPassword = (EditText) findViewById(R.id.passwordLogin);
        errorLogin = (TextView) findViewById(R.id.errorLogin);
        listenLoginButton();
    }

    /**
     * Check if login is true or false
     *
     * @param username
     * @param password
     */
    private void loginResult(String username, String password) {
        Log.d("method", "loginResult....");
        Boolean isLogin = this.userController.verifyLogin(username, password);
        if (!isLogin) errorLogin.setText("Nom d'utilisateur ou mot de passe incorrecte");
        else {
            errorLogin.setText("");
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Listen event in button login
     */
    private void listenLoginButton() {
        ((Button) findViewById(R.id.btnLogin)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /**
                 * Test Toast
                 */
                //Toast.makeText(LoginActivity.this,"Test",Toast.LENGTH_SHORT).show();
                /**
                 * View message log
                 */
                Log.d("Login page", "Click button login....");

                /**
                 * Get value of input
                 */

                String usernameValue = txtUsername.getText().toString();
                String passwordValue = txtPassword.getText().toString();

                loginResult(usernameValue, passwordValue);
            }
        });
    }
}
