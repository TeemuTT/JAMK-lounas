package ttt.opiskelijalounas.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jakewharton.threetenabp.AndroidThreeTen;

import ttt.opiskelijalounas.R;

@SuppressWarnings("ConstantConditions")
public class RestaurantActivity extends AppCompatActivity implements RestaurantActivityFragment.OnFragmentInteractionListener {

    private boolean isSinglePaneUI = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_restaurant));
        setSupportActionBar(toolbar);

        AndroidThreeTen.init(this);

        if (findViewById(R.id.fragmentContainer) != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new RestaurantActivityFragment())
                    .commit();
        } else {
            isSinglePaneUI = true;
            CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbarLayout.getLayoutParams();
            params.setScrollFlags(0);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (!isSinglePaneUI) {
            getSupportFragmentManager().popBackStack();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
            toolbarLayout.setTitle(getString(R.string.title_activity_restaurant));
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isSinglePaneUI) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
            toolbarLayout.setTitle(getString(R.string.title_activity_restaurant));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                CreateAboutDialog().show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @SuppressLint("InflateParams")
    private AlertDialog CreateAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.about_layout, null)).setTitle(getString(R.string.action_about));
        return builder.create();
    }

    @Override
    public void onFragmentInteraction(String sodexoId, String restaurant) {
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(restaurant);

        if (!isSinglePaneUI) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right)
                    .replace(R.id.fragmentContainer, MenuFragment.newInstance(sodexoId))
                    .addToBackStack(null)
                    .commit();
        } else {
            MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menuFragment);
            menuFragment.updateMenu(sodexoId);
        }
    }
}
