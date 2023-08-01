package first.app.e_tourisme.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

import first.app.e_tourisme.R;

public class SignInFragment3Activity extends AppCompatActivity {

    private EditText dateEditText;
    private Calendar selectedDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_fragment3);
        listenHaveAccountButton();
        dateEditText = findViewById(R.id.dateSignIn);
        dateEditText.setOnClickListener(v -> showDatePickerDialog());

        Button btnNext3 = findViewById(R.id.nextSignIn);
        btnNext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText dateSignIn = (EditText) findViewById(R.id.dateSignIn);
                String dateValue = dateSignIn.getText().toString();

                if (dateValue.isEmpty()) {
                    Toast.makeText(SignInFragment3Activity.this, "Veuillez indiquer votre date de naissance", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(SignInFragment3Activity.this, SignInFragment4Activity.class);
                    intent.putExtra("NameValue", getIntent().getStringExtra("NameValue"));
                    intent.putExtra("SurnameValue", getIntent().getStringExtra("SurnameValue"));
                    intent.putExtra("GenderValue", getIntent().getStringExtra("GenderValue"));
                    intent.putExtra("DateValue", dateValue);
                    startActivity(intent);
                }


            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            selectedDate.set(year, month, dayOfMonth);
            dateEditText.setText(formatDate(selectedDate.getTime()));
        }

        private String formatDate(Date date) {
            //  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            // return dateFormat.format(date);
            return date.toString();
        }
    };

    private void listenHaveAccountButton() {
        ((Button) findViewById(R.id.haveAccountBtn)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SignInFragment3Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}