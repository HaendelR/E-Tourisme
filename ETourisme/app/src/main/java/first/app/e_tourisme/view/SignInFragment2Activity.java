package first.app.e_tourisme.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import first.app.e_tourisme.R;

public class SignInFragment2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_fragment2);
        Button btnNext2 = findViewById(R.id.nextSignIn);
        listenHaveAccountButton();
        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioButton rdW = (RadioButton) findViewById(R.id.rdW);
                RadioButton rdM = (RadioButton) findViewById(R.id.rdM);

                if (!rdW.isChecked() && !rdM.isChecked()) {
                    Toast.makeText(SignInFragment2Activity.this, "Veuillez choisir l'un des deux ", Toast.LENGTH_LONG).show();
                } else {
                    Integer gender = 1;
                    if (rdW.isChecked()) {
                        gender = 0;
                    }

                    Intent intent = new Intent(SignInFragment2Activity.this, SignInFragment3Activity.class);
                    intent.putExtra("NameValue", getIntent().getStringExtra("NameValue"));
                    intent.putExtra("SurnameValue", getIntent().getStringExtra("SurnameValue"));
                    intent.putExtra("GenderValue", gender.toString());
                    startActivity(intent);
                }


            }
        });
    }

    private void listenHaveAccountButton() {
        ((Button) findViewById(R.id.haveAccountBtn)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignInFragment2Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}