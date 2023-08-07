package first.app.e_tourisme.view.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import first.app.e_tourisme.R;
import first.app.e_tourisme.tools.Authorization;
import first.app.e_tourisme.view.LoginActivity;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Authorization auth = new Authorization();
        if (!auth.verifyToken(requireActivity())) {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
        }
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebView webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript if needed

        // Load the HTML content from the file "home.html"
        String htmlContent = loadHTMLContentFromAsset();

        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }

    private String loadHTMLContentFromAsset() {
        try {
            InputStream inputStream = requireActivity().getAssets().open("home.html");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String videoPath = "file:///android_asset/utilisation.mp4"; // Chemin vers la vidéo dans le répertoire "assets"

            String htmlContent = new String(buffer, StandardCharsets.UTF_8);
            htmlContent = htmlContent.replace("{VIDEO_PATH}", videoPath);

            return htmlContent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
