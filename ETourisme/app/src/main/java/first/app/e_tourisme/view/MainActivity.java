package first.app.e_tourisme.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.io.File;

import first.app.e_tourisme.R;
import first.app.e_tourisme.tools.Authorization;
import first.app.e_tourisme.tools.Notifications;
import first.app.e_tourisme.view.ui.gallery.GalleryFragment;
import first.app.e_tourisme.view.ui.home.HomeFragment;
import first.app.e_tourisme.view.ui.listSite.ListFragment;
import first.app.e_tourisme.view.ui.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_NOTIFY_LOGOUT_KEY = "notifyLogOut";
    private DrawerLayout drawer;
    private String channel_id = "C01";

    private Boolean notifyLogOut;
    private String channel_name = "channel01";
    private String channel_desc = "channel desc";

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Authorization auth = new Authorization();
        if (!auth.verifyToken(MainActivity.this)) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        SharedPreferences preferences = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);
        notifyLogOut = preferences.getBoolean(PREF_NOTIFY_LOGOUT_KEY, false);

        if (notifyLogOut) {
            Notifications.createNotificationChannel(this, channel_id, channel_name, channel_desc);
        }


        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setHomeAsUpIndicator(R.drawable.ic_nav_drawer);

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                switchFragment(new HomeFragment());
                updateDrawerTitle(getString(R.string.menu_home));
            } else if (id == R.id.nav_list) {
                updateDrawerTitle(getString(R.string.menu_list_site));
                switchFragment(new ListFragment());
            } else if (id == R.id.nav_gallery) {
                updateDrawerTitle(getString(R.string.menu_galerie));
                switchFragment(new GalleryFragment());
            } else if (id == R.id.nav_settings) {
                switchFragment(new SettingsFragment());
                updateDrawerTitle(getString(R.string.menu_settings));
            } else if (id == R.id.nav_logOut) {
                showLogoutConfirmationDialog(notifyLogOut);
            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        if (savedInstanceState == null) {
            switchFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
            updateDrawerTitle(getString(R.string.menu_home));
        }
    }

    public void updateNotifyLogOut(boolean newValue) {
        notifyLogOut = newValue;
    }


    private void showLogoutConfirmationDialog(Boolean notifyLogOut) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Déconnexion");
        builder.setMessage("Voulez-vous vraiment vous déconnecter ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                traitLogOut(notifyLogOut);
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog (do nothing)
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void traitLogOut(Boolean notifyLogoutValue) {
        String filename = "token.txt";
        File file = new File(getApplicationContext().getFilesDir(), filename);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                // Échec de la suppression du fichier
                Log.d("Suppression", "Échec de déconnexion  ");
            }
        }

        if (notifyLogoutValue) {
            Notifications.showNotification(MainActivity.this, channel_id, "Statut", "Vous êtes déconnecté", LoginActivity.class);
        }
    }


    private void updateDrawerTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (currentFragment != null) {
            fragmentTransaction.remove(currentFragment);
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }


    /*private void switchFragment(Fragment fragment) {
        if (fragment.isAdded() || fragment.isDetached()) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
