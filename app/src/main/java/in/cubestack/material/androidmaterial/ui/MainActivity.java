package in.cubestack.material.androidmaterial.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import in.cubestack.android.lib.storm.service.asyc.StormCallBack;
import in.cubestack.material.androidmaterial.R;
import in.cubestack.material.androidmaterial.db.InitializerService;
import in.cubestack.material.androidmaterial.fragment.MainFragment;
import in.cubestack.material.androidmaterial.model.WordList;
import in.cubestack.material.androidmaterial.util.LogUtils;
import in.cubestack.material.androidmaterial.util.UiUtils;

/**
 * Sample application for Storm ORM.
 *
 * Check on Google play: https://play.google.com/store/apps/developer?id=Cube+Stack
 * Storm on GIT: https://github.com/supald/storm
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class MainActivity extends AbstractCubeStackActivity {

    private static final String TAG = "MAIN";
    private boolean exit = false;

    private ActionBarDrawerToggle drawerToggle;

    @Bind(R.id.view_nav_drawer)
    DrawerLayout drawerLayout;

    @Bind(R.id.view_nav)
    NavigationView navigationView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    MainFragment mf;


    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.app_name));
        initNavDrawer();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAddActivity();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        mf = MainFragment.newInstance();
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
                return menuHandler(menuItem);
            }
        });
    }

    private boolean menuHandler(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menuAdd:
                drawerLayout.closeDrawer(GravityCompat.START);
                launchAddActivity();
                break;
            case R.id.menuClean:
                drawerLayout.closeDrawer(GravityCompat.START);
                clean();
                reload();
                break;

            case R.id.menuPlay:
                drawerLayout.closeDrawer(GravityCompat.START);
                openPage("https://play.google.com/store/apps/developer?id=Cube+Stack");
                break;
            case R.id.menuGit:
                drawerLayout.closeDrawer(GravityCompat.START);
                openPage("http://github.com/supald/storm");
                break;
            case R.id.menuAboutUs:
                drawerLayout.closeDrawer(GravityCompat.START);
                openPage("http://cubestack.in");
                break;
        }
        return false;
    }


    private void launchAddActivity() {
        View v = drawerLayout;
        Intent intent = new Intent(this, AddWorkActivity.class);
        launchActivityForResult(this, intent, 1, v, new int[]{v.getWidth(), v.getHeight()});
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null  && data.getBooleanExtra("success", false)) {
            mf.updateData();
        }
    }

    private void reload () {
        Intent intent = new Intent(this, LauncherActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void go(String content, long time) {
        toast(content, Toast.LENGTH_LONG);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                MainActivity.launch(MainActivity.this);
            }
        }, 200);
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

    public void showSnackBar(String s) {
        if(fab != null ) {
            Snackbar snackbar = Snackbar.make(
                    (fab), s,
                    Snackbar.LENGTH_SHORT);
            View view = snackbar.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackbar.show();

        }
    }
}
