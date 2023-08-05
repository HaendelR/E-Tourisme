package first.app.e_tourisme.view.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import first.app.e_tourisme.R;
import first.app.e_tourisme.controller.MediaSiteController;
import first.app.e_tourisme.model.MediaSite;
import first.app.e_tourisme.tools.GalleryAdapter;
import first.app.e_tourisme.tools.MediaSiteListener;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;
    private ProgressBar loadingProgressBar;

    private MediaSiteController mediaSiteController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        this.mediaSiteController = MediaSiteController.getInstance();
        loadingProgressBar = view.findViewById(R.id.progressBar);
        setupRecyclerView();
        fetchMediaSites();
        return view;
    }

    private void setupRecyclerView() {
        galleryAdapter = new GalleryAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(galleryAdapter);
    }

    private void fetchMediaSites() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        this.mediaSiteController.getMediaSites(new MediaSiteListener() {
            @Override
            public void onMediaSitesLoaded(List<MediaSite> mediaSites) {
                galleryAdapter.setMediaSites(mediaSites);
                loadingProgressBar.setVisibility(View.GONE);
            }
        });

    }
}
