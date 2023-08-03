package first.app.e_tourisme.view.ui.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import first.app.e_tourisme.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getActivity().setTheme(R.style.settings);
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}