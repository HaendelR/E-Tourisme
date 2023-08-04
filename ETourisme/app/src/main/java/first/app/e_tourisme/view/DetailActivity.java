package first.app.e_tourisme.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import first.app.e_tourisme.R;
import first.app.e_tourisme.model.TouristicSite;


public class DetailActivity extends AppCompatActivity {

    TextView nomSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.details);
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
        getSupportActionBar().setTitle("Détail du site");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Gérer le clic sur le bouton de retour ici
            finish(); // Terminez l'activité actuelle pour revenir en arrière
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
