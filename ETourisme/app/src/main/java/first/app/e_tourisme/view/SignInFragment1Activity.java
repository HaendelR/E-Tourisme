package first.app.e_tourisme.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import first.app.e_tourisme.R;

public class SignInFragment1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_fragment1);
        Button btnNext1 = findViewById(R.id.nextSignIn);
        listenHaveAccountButton();
        btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameSignIn = (EditText) findViewById(R.id.nameSignIn);
                String nameValue = nameSignIn.getText().toString();
                EditText surnameSignIn = (EditText) findViewById(R.id.surnameSignIn);
                String surnameValue = surnameSignIn.getText().toString();

                if (nameValue.length() == 0 || surnameValue.length() == 0) {
                    Toast.makeText(SignInFragment1Activity.this, "Veuillez remplir tous les champs ", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(SignInFragment1Activity.this, SignInFragment2Activity.class);
                    intent.putExtra("NameValue", nameValue);
                    intent.putExtra("SurnameValue", surnameValue);
                    startActivity(intent);
                }
            }

        });
    }

    private void listenHaveAccountButton() {
        ((Button) findViewById(R.id.haveAccountBtn)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignInFragment1Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}