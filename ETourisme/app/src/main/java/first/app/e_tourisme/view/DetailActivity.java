package first.app.e_tourisme.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import first.app.e_tourisme.R;
import first.app.e_tourisme.model.TouristicSite;


public class DetailActivity extends AppCompatActivity {

    TextView nomSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initDetail();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("siteTouristique")) {
            TouristicSite site = intent.getParcelableExtra("siteTouristique");
            nomSite.setText(site.getName());
        }
    }

    private void initDetail() {
        nomSite = (TextView) findViewById(R.id.nomSite);
    }
}