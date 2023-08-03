package first.app.e_tourisme.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import first.app.e_tourisme.R;
import first.app.e_tourisme.controller.ListeController;
import first.app.e_tourisme.model.TouristicSite;
import first.app.e_tourisme.tools.CustomListAdapter;
import first.app.e_tourisme.tools.ListeCallBack;

public class ListeActivity extends AppCompatActivity {

    private ProgressBar loadingProgressBar;
    private ListView listeTouristic;

    private ListeController listeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);
        initListe();
        this.listeController = ListeController.getInstance();
        getListTouristiques();
    }

    private void initListe() {
        listeTouristic = (ListView) findViewById(R.id.listView);
        loadingProgressBar = findViewById(R.id.progressBar);
    }

    private void getListTouristiques() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        this.listeController.getAllSites(new ListeCallBack() {
            @Override
            public void onListeResult(List<TouristicSite> sites) {
                //List<TouristicSite> listeSite = getListTouristiques();
                listeTouristic.setAdapter(new CustomListAdapter(ListeActivity.this, sites));

                listeTouristic.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        Object o = listeTouristic.getItemAtPosition(position);
                        TouristicSite site = (TouristicSite) o;
                        Toast.makeText(ListeActivity.this, "Selected :" + " " + site, Toast.LENGTH_LONG).show();
                    }
                });
                loadingProgressBar.setVisibility(View.GONE);
            }
        });
    }
}