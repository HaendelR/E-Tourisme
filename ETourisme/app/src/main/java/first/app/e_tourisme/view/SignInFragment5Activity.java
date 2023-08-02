package first.app.e_tourisme.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import first.app.e_tourisme.R;
import first.app.e_tourisme.controller.UserController;
import first.app.e_tourisme.model.User;
import first.app.e_tourisme.tools.SignInCallBack;

public class SignInFragment5Activity extends AppCompatActivity {
    private UserController userController;
    private TextView errorSignIn;
    private Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_fragment5);
        initSignIn();
        this.userController = UserController.getInstance();

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameSignIn = findViewById(R.id.usernameSignIn);
                String usernameValue = usernameSignIn.getText().toString();
                EditText passwordSignIn = findViewById(R.id.passwordSignIn);
                String passwordValue = passwordSignIn.getText().toString();
                EditText passwordConfirmSignIn = findViewById(R.id.passwordConfirmSignIn);
                String passwordConfirmValue = passwordConfirmSignIn.getText().toString();

                if (usernameValue.isEmpty()) {
                    Toast.makeText(SignInFragment5Activity.this, "Veuillez saisir votre identifiant", Toast.LENGTH_LONG).show();
                } else if (passwordValue.isEmpty()) {
                    Toast.makeText(SignInFragment5Activity.this, "Veuillez saisir votre mot de passe", Toast.LENGTH_LONG).show();
                } else if (!passwordValue.equals(passwordConfirmValue)) {
                    Toast.makeText(SignInFragment5Activity.this, "Les deux mots de passe ne sont pas identiques", Toast.LENGTH_LONG).show();
                } else {
                    // Récupérer toutes les valeurs transmises depuis les pages précédentes
                    String nameValue = getIntent().getStringExtra("NameValue");
                    String surnameValue = getIntent().getStringExtra("SurnameValue");
                    Integer genderValue = Integer.valueOf(getIntent().getStringExtra("GenderValue"));
                    String dateValue = getIntent().getStringExtra("DateValue");
                    String emailValue = getIntent().getStringExtra("EmailValue");
                    String adressValue = getIntent().getStringExtra("AdressValue");
                    Integer contactValue = Integer.valueOf(getIntent().getStringExtra("ContactValue"));

                    // Format date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                    Date dateFormatted = null;
                    try {
                        dateFormatted = dateFormat.parse(dateValue);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    User userCreate = new User(nameValue, surnameValue, usernameValue, genderValue, adressValue, emailValue, contactValue, dateFormatted, passwordValue);
                    signInUserProcess(userCreate);
                }
            }
        });
    }

    private void initSignIn() {
        errorSignIn = (TextView) findViewById(R.id.errorSignIn);
        btnFinish = findViewById(R.id.finishSignIn);
        listenHaveAccountButton();

    }

    private void signInUserProcess(User user) {
        this.userController.signInUser(user, new SignInCallBack() {

            @Override

            public void onSignInResult(boolean success) {
                if (success) {
                    Intent intent = new Intent(SignInFragment5Activity.this, LoginActivity.class);
                    intent.putExtra("success", "Enregistrement réussi ");
                    startActivity(intent);
                } else {
                    errorSignIn.setText("Échec de l'enregistrement");
                }
            }

        });
    }

    private void listenHaveAccountButton() {
        ((Button) findViewById(R.id.haveAccountBtn)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignInFragment5Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}