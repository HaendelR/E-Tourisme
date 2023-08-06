package first.app.e_tourisme.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import first.app.e_tourisme.R;
import first.app.e_tourisme.controller.CommentaireController;
import first.app.e_tourisme.model.Commentaire;
import first.app.e_tourisme.model.TouristicSite;
import first.app.e_tourisme.model.User;
import first.app.e_tourisme.tools.CommentCallBack;


public class DetailActivity extends AppCompatActivity {

    TextView nomSite;

    ImageView imageSite;

    TextView placeSite;

    TextView descriptionSite;

    EditText commentaire;

    private CommentaireController commentaireController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.details);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initDetail();
        getDetailSite();
        this.commentaireController = CommentaireController.getInstance();
    }

    private void initDetail() {
        nomSite = (TextView) findViewById(R.id.nomSite);
        imageSite = (ImageView) findViewById(R.id.imageDetail);
        placeSite = (TextView) findViewById(R.id.lieu);
        descriptionSite = (TextView) findViewById(R.id.description);
        getSupportActionBar().setTitle("Détail du site");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        commentaire = (EditText) findViewById(R.id.contenuCommentaire);
        listenBoutonComment();
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

    public void addCommentResult(Commentaire comment, TouristicSite site) {
        this.commentaireController.addComment(comment, new CommentCallBack() {
            @Override
            public void addCommentResult(boolean success) {
                if(success) {
                    Toast.makeText(DetailActivity.this,"Commentaire inséré avec succès", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
                    intent.putExtra("siteTouristique", site);
                    startActivity(intent);
                } else {
                    Toast.makeText(DetailActivity.this,"Commentaire non inséré", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void listenBoutonComment() {
        User userconnecte = readUserFromFile();
        Commentaire com = new Commentaire();
        TouristicSite site = getIntent().getParcelableExtra("siteTouristique");
        ((Button) findViewById(R.id.boutonCommentaire)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               String contentCommentaire = commentaire.getText().toString();
               com.setContent(contentCommentaire);
               com.setUserName(userconnecte.getName());
               com.setUserSurname(userconnecte.getSurname());
               com.setTouristicSiteName(site.getName());

                addCommentResult(com, site);
            }
        });
    }

    private User readUserFromFile() {
        try {
            FileInputStream fileInputStream = openFileInput("user_data.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();

            String userJson = stringBuilder.toString();

            Gson gson = new Gson();
            User user = gson.fromJson(userJson, User.class);

            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
