package in.cubestack.material.androidmaterial.db;

import android.content.Context;

import in.cubestack.android.lib.storm.core.DatabaseMetaData;
import in.cubestack.android.lib.storm.core.MetaDataReader;
import in.cubestack.android.lib.storm.service.asyc.AsyncSupportService;
import in.cubestack.material.androidmaterial.util.LogUtils;

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
