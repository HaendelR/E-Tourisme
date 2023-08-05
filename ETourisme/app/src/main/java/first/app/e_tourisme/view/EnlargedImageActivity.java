package first.app.e_tourisme.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import first.app.e_tourisme.R;

public class EnlargedImageActivity extends AppCompatActivity {
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarged_image);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Récupérer l'URI du fichier de l'image agrandie depuis l'intent
        Uri imageUri = getIntent().getParcelableExtra("imageUri");
        String touristicName = getIntent().getStringExtra("imageName");
        getSupportActionBar().setTitle(touristicName);


        // Afficher l'image agrandie
        ImageView imageView = findViewById(R.id.enlargedImageView);
        imageView.setImageURI(imageUri);

        // Sauvegarder le fichier temporaire
        tempFile = new File(imageUri.getPath());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Supprimer le fichier temporaire lorsque l'activité est détruite
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }
}
