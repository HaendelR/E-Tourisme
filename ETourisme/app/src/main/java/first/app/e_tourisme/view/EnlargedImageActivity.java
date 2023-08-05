package first.app.e_tourisme.view;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import first.app.e_tourisme.R;

public class EnlargedImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarged_image);

        getSupportActionBar().setTitle("Photo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.enlargedImageView);

        // Récupérer l'ID de ressource de l'image agrandie depuis l'intent
        int imageResId = getIntent().getIntExtra("imageResId", 0);

        // Afficher l'image agrandie
        imageView.setImageResource(imageResId);
    }
}
