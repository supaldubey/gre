package in.cubestack.material.androidmaterial.ui;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.cubestack.android.lib.storm.service.asyc.StormCallBack;
import in.cubestack.material.androidmaterial.R;
import in.cubestack.material.androidmaterial.model.WordList;
import in.cubestack.material.androidmaterial.util.UiUtils;

/**
 * Created by cubestack on 9/8/2015.
 */
public abstract class AbstractCubeStackActivity extends AppCompatActivity implements Toastable{

    private ProgressDialog progressDialog;

    private boolean destroyed = false;

    @Nullable
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceLayout());
        ButterKnife.bind(this);
        initToolbar();
    }

    protected void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            setNavigationIcon(getToolBarIcon());
        }
    }

    protected void setNavigationIcon(Drawable drawable) {
        if(drawable != null) mToolbar.setNavigationIcon(drawable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    // ***************************************
    // Public methods
    // ***************************************
    public void showLoadingProgressDialog() {
        this.showProgressDialog("Loading. Please wait...");
    }

    public void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }

    public void toast(String message) {
        toast(message, Toast.LENGTH_SHORT);
    }

    public void toast(String message, int duration) {
        Toast toast = Toast.makeText(this, message, duration);
        TextView toastView = (TextView) LayoutInflater.from(this).inflate(R.layout.toast, null);
        toastView.setText(message);
        toast.setView(toastView);
        toast.show();
    }

    public void toast(int size, long time) {
        toast("Fetched " + size + " records in " + (time) + " ms");
    }

    @Override
    public void showLoadingProgressDialog(boolean closeable) {
        this.showLoadingProgressDialog();
        progressDialog.setCancelable(closeable);
    }

    @Override
    public void showProgressDialog(CharSequence message, boolean closeable) {
        this.showProgressDialog(message);
        progressDialog.setCancelable(closeable);
    }

    @Override
    public void updateProgressDialogMessage(String msg) {
        if (progressDialog != null && !destroyed) progressDialog.setMessage(msg);
    }

    protected abstract int getResourceLayout();

    Drawable getToolBarIcon() {
        return UiUtils.getDrawable(this, R.drawable.menu);
    }

    public static void launchActivityForResult(AbstractCubeStackActivity fromActivity, Intent intent, int reqCode, View view, int[] dimen) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, dimen[0], dimen[1]);
        ActivityCompat.startActivityForResult(fromActivity, intent, reqCode, optionsCompat.toBundle());
    }

    protected void wobble(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.wobble));
    }


    protected void openPage(String url) {
        Intent web = new Intent(Intent.ACTION_VIEW);
        web.setData(Uri.parse(url));
        startActivity(web);

    }

    public  void clean() {
        final long time = System.currentTimeMillis();
        MainApplication.service().truncate(WordList.class, new StormCallBack<WordList>() {
            @Override
            public void onDelete(int deletedRows) {
                toast(String.format("Cleaned entire in %s milliseconds", ""+(System.currentTimeMillis() - time) ));
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    protected void launchDelayed(long[] stats) {
        StringBuilder sb = new StringBuilder("Initialized successfully.<br/>");
        sb.append("Persisted total ")
                .append(stats[1])
                .append(" entities<br/>")
                .append("Consisting of ")
                .append(stats[0])
                .append(" database inserts across relations <br/>")
                .append("In time ")
                .append(stats[2])
                .append(" milli seconds <br/>")
                .append("Mind Blown or \"Storm\"ed or Both!");
        go(sb.toString(), 2000);
    }

    protected  void go(String content, long time) {}

}
