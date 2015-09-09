package in.cubestack.material.androidmaterial.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import in.cubestack.android.lib.storm.service.asyc.StormCallBack;
import in.cubestack.material.androidmaterial.R;
import in.cubestack.material.androidmaterial.db.InitializerService;
import in.cubestack.material.androidmaterial.model.WordList;
import in.cubestack.material.androidmaterial.util.LogUtils;

public class LauncherActivity extends AbstractCubeStackActivity {

    protected static final String TAG = LogUtils.makeLogTag(LauncherActivity.class);

    @Bind(R.id.launcherLogo)
    ImageView launcherLogo;

    @Bind(R.id.stats)
    TextView statsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.service().count(WordList.class, new StormCallBack() {
            @Override
            public void onAggregate(int result) {
                if (result == 0) {
                    try {
                        long[] stats = new InitializerService().initialize(LauncherActivity.this);
                        launchDelayed(stats);
                    } catch (Exception e) {
                        LogUtils.LOGE(TAG, "Error while initializing db", e);
                    }
                } else {
                    go("Database already exists", 1000);
                }
            }
        });
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_launcher;
    }

    private void launchDelayed(long[] stats) {
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

    private void go(final String msg, final long time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statsView.setText(Html.fromHtml(msg));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.launch(LauncherActivity.this);
                        finish();
                    }
                }, time);
            }
        });
    }
}
