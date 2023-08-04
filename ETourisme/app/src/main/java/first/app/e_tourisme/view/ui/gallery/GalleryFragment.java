package first.app.e_tourisme.view.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import first.app.e_tourisme.R;
import first.app.e_tourisme.tools.GalleryAdapter;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;

    // Exemple de liste d'images
    private int[] images = {
            R.drawable.ic_etourimse,
            R.drawable.ic_etourimse2,
            R.drawable.ic_etourimse3,
            R.drawable.ic_etourimse4,
            // Ajoutez ici d'autres images...
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        galleryAdapter = new GalleryAdapter(images);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(galleryAdapter);
    }
}
