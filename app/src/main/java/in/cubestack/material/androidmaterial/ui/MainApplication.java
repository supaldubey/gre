package in.cubestack.material.androidmaterial.ui;

import android.app.Application;
import android.content.res.Configuration;

import in.cubestack.material.androidmaterial.db.WordStackAsyncService;

/**
 * Created by cubestack on 9/8/2015.
 */
public class MainApplication extends Application {

    private static MainApplication instance;

    private static WordStackAsyncService wordStackService;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        wordStackService = new WordStackAsyncService(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static MainApplication instance() {
        return instance;
    }

    public static WordStackAsyncService service() {
        return wordStackService;
    }
}
