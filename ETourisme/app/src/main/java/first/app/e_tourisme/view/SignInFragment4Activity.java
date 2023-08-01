package first.app.e_tourisme.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import first.app.e_tourisme.R;

public class SignInFragment4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_fragment4);
        Button btnNext4 = findViewById(R.id.nextSignIn);

        listenHaveAccountButton();
        btnNext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText addressSignIn = (EditText) findViewById(R.id.adressSignIn);
                String addressValue = addressSignIn.getText().toString();
                EditText contactSignIn = (EditText) findViewById(R.id.contactSignIn);
                Integer contactValue = 0;
                if (contactSignIn.getText().toString().length() != 0) {
                    contactValue = Integer.parseInt(contactSignIn.getText().toString());
                }

                if (addressValue.length() == 0) {
                    Toast.makeText(SignInFragment4Activity.this, "Veuillez indiquer votre adresse", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(SignInFragment4Activity.this, SignInFragment5Activity.class);
                    intent.putExtra("NameValue", getIntent().getStringExtra("NameValue"));
                    intent.putExtra("SurnameValue", getIntent().getStringExtra("SurnameValue"));
                    intent.putExtra("GenderValue", getIntent().getStringExtra("GenderValue"));
                    intent.putExtra("DateValue", getIntent().getStringExtra("DateValue"));
                    intent.putExtra("AddressValue", addressValue);
                    intent.putExtra("ContactValue", contactValue.toString());
                    startActivity(intent);
                }
            }
        });
    }

    private void listenHaveAccountButton() {
        ((Button) findViewById(R.id.haveAccountBtn)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignInFragment4Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}