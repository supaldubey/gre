package in.cubestack.material.androidmaterial.db;

import android.content.Context;

import in.cubestack.android.lib.storm.core.DatabaseMetaData;
import in.cubestack.android.lib.storm.core.MetaDataReader;
import in.cubestack.android.lib.storm.service.BaseService;
import in.cubestack.material.androidmaterial.util.LogUtils;

/**
 * Created by arunk on 7/10/2015.
 */
public class WordStackSyncService extends BaseService {

    protected static final String TAG = LogUtils.makeLogTag(WordStackSyncService.class);

    private WordStackSyncService(Context context, DatabaseMetaData databaseMetaData) {
        super(context, databaseMetaData);
        LogUtils.LOGD(TAG, "Service created");
    }

    public WordStackSyncService(Context context) {
        this(context, new MetaDataReader().fetchDatabaseMetaData(WordStackDatabase.class));
        LogUtils.LOGD(TAG, "Service created");
    }

}
