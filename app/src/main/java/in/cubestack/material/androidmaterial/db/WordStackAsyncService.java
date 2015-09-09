package in.cubestack.material.androidmaterial.db;

import android.content.Context;

import in.cubestack.android.lib.storm.core.DatabaseMetaData;
import in.cubestack.android.lib.storm.core.MetaDataReader;
import in.cubestack.android.lib.storm.service.asyc.AsyncSupportService;
import in.cubestack.material.androidmaterial.util.LogUtils;

/**
 * Created by arunk on 7/10/2015.
 */
public class WordStackAsyncService extends AsyncSupportService {

    protected static final String TAG = LogUtils.makeLogTag(WordStackAsyncService.class);

    private WordStackAsyncService(Context context, DatabaseMetaData databaseMetaData) {
        super(context, databaseMetaData);
        LogUtils.LOGD(TAG, "Service created");
    }

    public WordStackAsyncService(Context context) {
        this(context, new MetaDataReader().fetchDatabaseMetaData(WordStackDatabase.class));
        LogUtils.LOGD(TAG, "Service created");
    }

}
