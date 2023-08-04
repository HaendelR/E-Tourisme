package first.app.e_tourisme.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import first.app.e_tourisme.R;
import first.app.e_tourisme.model.TouristicSite;


public class DetailActivity extends AppCompatActivity {

    TextView nomSite;

    ImageView imageSite;

    TextView placeSite;

    TextView descriptionSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.details);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initDetail();
        getDetailSite();
    }

    private void initDetail() {
        nomSite = (TextView) findViewById(R.id.nomSite);
        imageSite = (ImageView) findViewById(R.id.imageDetail);
        placeSite = (TextView) findViewById(R.id.lieu);
        descriptionSite = (TextView) findViewById(R.id.description);
        getSupportActionBar().setTitle("Détail du site");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int getMipmapResIdByName(String resName)  {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName , "mipmap", pkgName);
        return resID;
    }

    public void getDetailSite() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("siteTouristique")) {
            TouristicSite site = intent.getParcelableExtra("siteTouristique");
            nomSite.setText(site.getName());
            placeSite.setText(site.getPlace().getEntitled());
            descriptionSite.setText(site.getDescription());
            int imageId = this.getMipmapResIdByName("site");
            imageSite.setImageResource(imageId);

        }
    }

}
