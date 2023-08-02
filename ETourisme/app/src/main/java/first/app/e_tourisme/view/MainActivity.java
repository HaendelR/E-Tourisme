package first.app.e_tourisme.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import first.app.e_tourisme.R;
import first.app.e_tourisme.view.ui.gallery.GalleryFragment;
import first.app.e_tourisme.view.ui.home.HomeFragment;
import first.app.e_tourisme.view.ui.slideshow.SlideshowFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Afficher le bouton de navigation dans l'ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setHomeAsUpIndicator(R.drawable.ic_nav_drawer);

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Gérer l'action lors de la sélection de "Home"
                switchFragment(new HomeFragment());
                updateDrawerTitle(getString(R.string.nav_home));
            } else if (id == R.id.nav_gallery) {
                // Gérer l'action lors de la sélection de "Gallery"
                switchFragment(new GalleryFragment());
            } else if (id == R.id.nav_slideshow) {
                // Gérer l'action lors de la sélection de "Slideshow"
                switchFragment(new SlideshowFragment());
            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        // Afficher le fragment par défaut au démarrage de l'application
        if (savedInstanceState == null) {
            switchFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
            updateDrawerTitle(getString(R.string.nav_home));
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
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Gérer les événements du bouton de navigation dans l'ActionBar
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
