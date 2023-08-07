package first.app.e_tourisme.view.ui.listSite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import first.app.e_tourisme.R;
import first.app.e_tourisme.controller.ListeController;
import first.app.e_tourisme.model.MediaSite;
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
                                Bitmap imageBitmap = MediaSite.decodeImageData(site.getImageData());
                                if(imageBitmap != null) {
                                    File file = getFileByImageName(site.getName());
                                    if(file != null) {
                                        Intent intent = new Intent(requireActivity(), DetailActivity.class);
                                        intent.putExtra("imageUri", Uri.fromFile(file));
                                        intent.putExtra("siteTouristique", site);
                                        startActivity(intent);
                                    } else {
                                        File saveFile = saveBitmapToFile(getContext(), imageBitmap, site.getName());
                                        Intent intent = new Intent(requireActivity(), DetailActivity.class);
                                        intent.putExtra("imageUri", Uri.fromFile(saveFile));
                                        intent.putExtra("siteTouristique", site);
                                        startActivity(intent);
                                    }
                                }

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
    private File getFileByImageName(String imageName) {
        Context context = getContext();
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            File[] files = cacheDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().equals(imageName)) {
                        return file;
                    }
                }
            }
        }
        return null;
    }


    private File saveBitmapToFile(Context context, Bitmap bitmap, String imageName) {
        try {
            File file = File.createTempFile(imageName, ".jpg", context.getCacheDir());
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
