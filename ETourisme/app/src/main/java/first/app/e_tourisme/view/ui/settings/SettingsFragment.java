package first.app.e_tourisme.view.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import first.app.e_tourisme.R;
import first.app.e_tourisme.tools.Authorization;
import first.app.e_tourisme.tools.OnNotifyLogoutChangedListener;
import first.app.e_tourisme.view.LoginActivity;
import first.app.e_tourisme.view.MainActivity;

public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String PREF_NOTIFY_LOGOUT_KEY = "notifyLogOut";
    private OnNotifyLogoutChangedListener notifyLogoutChangedListener;

    public void setOnNotifyLogoutChangedListener(OnNotifyLogoutChangedListener listener) {
        notifyLogoutChangedListener = listener;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        Authorization auth = new Authorization();
        if (!auth.verifyToken(requireActivity())) {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
        }
        // Retrieve current value of SwitchPreferenceCompat
        SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
        boolean notifyLogoutValue = preferences.getBoolean(PREF_NOTIFY_LOGOUT_KEY, false);

        // Add a listener to track changes in SwitchPreferenceCompat
        SwitchPreferenceCompat notifyLogoutSwitch = findPreference(PREF_NOTIFY_LOGOUT_KEY);
        if (notifyLogoutSwitch != null) {
            notifyLogoutSwitch.setChecked(notifyLogoutValue);
            notifyLogoutSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean value = (boolean) newValue;

                    // Save new value on SharedPreferences
                    SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(PREF_NOTIFY_LOGOUT_KEY, value);
                    editor.apply();

                    // Informer le MainActivity des changements
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null) {
                        mainActivity.updateNotifyLogOut(value);
                    }

                    return true;
                }

            });
        }
    }
}
