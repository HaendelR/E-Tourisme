package first.app.e_tourisme.view.ui.listSite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

import first.app.e_tourisme.R;
import first.app.e_tourisme.controller.ListeController;
import first.app.e_tourisme.model.TouristicSite;
import first.app.e_tourisme.tools.CustomListAdapter;
import first.app.e_tourisme.tools.ListeCallBack;
import first.app.e_tourisme.view.DetailActivity;

public class ListFragment extends Fragment {
    private ProgressBar loadingProgressBar;

    private ListView listeTouristic;

    private ListeController listeController;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initListe(view);
        this.listeController = ListeController.getInstance();
        getListTouristiques();
        return view;

    }

    private void initListe(View view) {
        listeTouristic = view.findViewById(R.id.listView);
        loadingProgressBar = view.findViewById(R.id.progressBar);
    }

    private void getListTouristiques() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        this.listeController.getAllSites(new ListeCallBack() {
            @Override
            public void onListeResult(List<TouristicSite> sites) {
                //List<TouristicSite> listeSite = getListTouristiques();
                listeTouristic.setAdapter(new CustomListAdapter(requireActivity(), sites));

                listeTouristic.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                        Object o = listeTouristic.getItemAtPosition(position);
                        TouristicSite site = (TouristicSite) o;
                        //Toast.makeText(requireActivity(), "Selected :" + " " + site, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(requireActivity(), DetailActivity.class);
                        intent.putExtra("siteTouristique", site); // Passer l'objet TouristicSite en tant que paramètre supplémentaire

                        startActivity(intent);
                    }
                });
                loadingProgressBar.setVisibility(View.GONE);
            }

        });
    }
}