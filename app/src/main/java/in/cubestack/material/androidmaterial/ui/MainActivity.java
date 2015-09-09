package in.cubestack.material.androidmaterial.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import butterknife.Bind;
import in.cubestack.material.androidmaterial.R;
import in.cubestack.material.androidmaterial.fragment.MainFragment;
import in.cubestack.material.androidmaterial.util.UiUtils;

/**
 * Created by cubestack on 9/8/2015.
 */
public class MainActivity extends AbstractCubeStackActivity {

    private boolean exit = false;

    private ActionBarDrawerToggle drawerToggle;

    @Bind(R.id.view_nav_drawer)
    DrawerLayout drawerLayout;

    @Bind(R.id.view_nav)
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.app_name));
        initNavDrawer();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MainFragment mf = MainFragment.newInstance();
        ft.add(R.id.content_fragment, mf);
        ft.commit();
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_main;
    }

    public static void launch(AbstractCubeStackActivity from) {
        Intent intent = new Intent(from, MainActivity.class);
        ActivityCompat.startActivity(from, intent, new Bundle());
    }

    private void initNavDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuShuffler:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO
                        break;
                    case R.id.menuDeck:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO
                        break;
                    case R.id.menuFavorites:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO
                        break;
                    case R.id.menuPractices:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO
                        break;
                    case R.id.menuAboutUs:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO
                        break;
                    case R.id.menuLegal:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //TODO
                        break;
                }
                return false;
            }
        });
    }

    @Override
    Drawable getToolBarIcon() {
        return UiUtils.getDrawable(this, R.drawable.menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
