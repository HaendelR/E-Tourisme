package first.app.e_tourisme.view.ui.listSite;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import java.util.List;

import first.app.e_tourisme.R;
import first.app.e_tourisme.controller.ListeController;
import first.app.e_tourisme.model.TouristicSite;
import first.app.e_tourisme.tools.Authorization;
import first.app.e_tourisme.tools.CustomListAdapter;
import first.app.e_tourisme.tools.ListeCallBack;
import first.app.e_tourisme.view.DetailActivity;
import first.app.e_tourisme.view.LoginActivity;

public class ListFragment extends Fragment {
    private ProgressBar loadingProgressBar;

    private ListView listeTouristic;

    private ListeController listeController;
    private EditText searchEditText;
    private CustomListAdapter customListAdapter;

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
        searchEditText = view.findViewById(R.id.searchEditText);
        Authorization auth = new Authorization();
        if (!auth.verifyToken(requireActivity())) {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void getListTouristiques() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        this.listeController.getAllSites(new ListeCallBack() {
            @Override
            public void onListeResult(List<TouristicSite> sites) {
                if (isAdded()) {
                    customListAdapter = new CustomListAdapter(requireActivity(), sites);
                    listeTouristic.setAdapter(customListAdapter);
                    listeTouristic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            if (isAdded()) {
                                Object o = listeTouristic.getItemAtPosition(position);
                                TouristicSite site = (TouristicSite) o;
                                Intent intent = new Intent(requireActivity(), DetailActivity.class);
                                intent.putExtra("siteTouristique", site);
                                startActivity(intent);
                            }
                        }
                    });
                    searchEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            customListAdapter.filter(charSequence.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    loadingProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
